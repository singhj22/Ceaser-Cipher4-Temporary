import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;


public class cypher4 
{
	public static void main(String[] args) throws IOException
	{
		String mes="";
		Boolean check= true;
		Scanner user= new Scanner(System.in);
		Random rand= new Random();
		
		System.out.print("Would you like to encrypt, decrypt, or crack: ");
		String choice= user.nextLine();
		String choice2 = "";
		char[] perm_arr = new char[26];
		char tChar; 
		int random;
		
		if (choice.equals("encrypt") || choice.equals("decrypt"))
		{
			System.out.print("Which algorithm would you like to use? ('c' for caesar, 'p' for permutation) ");
			choice2 = user.next();
			if (choice2.equals("p") && choice.equals("encrypt")) 
			{
				System.out.println("The key used for the permutation is: ");
				for (int i = 0; i < 26; i++)
					perm_arr[i] = (char) ('a' + i); 
				for (int i = 0; i < 26; i++)
				{
					random = rand.nextInt(25);
					tChar = perm_arr[i];
					perm_arr[i] = perm_arr[random];
					perm_arr[random] = tChar;
				}
				for (int i = 0; i < 26; i++) 
					System.out.print(perm_arr[i]);
			}
		}
		
		if (choice.equals("crack") || choice.equals("Crack")) 
		{
			int decrypt = 0;
			String decryptedMessage = "";
			System.out.println("Enter a filename to crack: ");
			String file = user.nextLine();
			System.out.println("-------------");
			String [] cipher = ceaser_cipher(file, false, decrypt).split("\n");
			for (int i = 0; i < cipher.length; i++)
				System.out.println(cipher[i].substring(0, (int) (cipher[i].length())));
				System.out.println("-------------");
				System.out.print("Does this look right?: ");
				String user_check  = user.next();
			while (user_check.equals("no") || user_check.equals("No")) 
			{
				++decrypt;
				System.out.println("-------------");
				cipher = ceaser_cipher(file, false, decrypt).split("\n");
				for (int i = 0; i < cipher.length; i++)
					mes= cipher[i].substring(0, (int) (cipher[i].length()));
					System.out.println(mes);
					System.out.println("-------------");
					System.out.print("Does this look like right?: ");
					user_check = user.next();
			}
			File n_userfile= new File(file.substring(0,file.length()-4) + "_DEC.txt");
			PrintWriter created= new PrintWriter(n_userfile);
			created.println(mes);
			created.close();
			System.out.println("Result written to "+ file.substring(0,file.length()-4) + "_DEC.txt");

		}
		else if (choice.equals("decrypt") || choice.equals("Decrypt"))
		{
			System.out.print("What file would you like to decrypt: ");
			String file = user.next();
			File n_userfile= new File(file.substring(0,file.length()-4) + "_DEC.txt");
			PrintWriter created= new PrintWriter(n_userfile);
			check= false;
			if (choice2.equals("c"))
			{
				System.out.print("How many places should the alphabet be shifted: ");
				int shiftedBy= user.nextInt();
				mes+=ceaser_cipher(file, check, shiftedBy);
			}
			else
				mes += perm_cipher(file, check, perm_arr);
			for (int i = 0; i < mes.length(); i++) 
			{
				if (mes.charAt(i) == '\n') 
					created.println();
				else
					created.print(mes.charAt(i));
			}
			System.out.println(mes);
			System.out.println("Result written to "+ file.substring(0,file.length()-4) + "_DEC.txt");
			created.close();
		}
		else
		{
			System.out.println("What file would you like to encrypt: ");
			String file = user.next();
			File n_userfile= new File(file.substring(0,file.length()-4) + "_ENC.txt");
			PrintWriter created= new PrintWriter(n_userfile);
			if (choice2.equals("c"))
			{
				System.out.print("How many places should the alphabet be shifted: ");
				int shiftedBy= user.nextInt();
				mes+=ceaser_cipher(file, check, shiftedBy);
			}
			else
			{
				mes += perm_cipher(file, check, perm_arr);
			}
			for (int i = 0; i < mes.length(); i++) 
			{
				if (mes.charAt(i) == '\n') 
					created.println();
				else
					created.print(mes.charAt(i));
			}
			System.out.println(mes);
			System.out.println("Result written to "+ file.substring(0,file.length()-4) + "_ENC.txt");
			created.close();
		}
	}	
	public static String ceaser_cipher(String filename, boolean encrypt, int shiftAmount) throws IOException
	{
		String rline;
		int hold;
		char rchar;
		String strF = "";
		Scanner readFrom= new Scanner(new File(filename));

		if (encrypt==true)
		{
			while(readFrom.hasNext())
			{
				rline= readFrom.nextLine();
				for (int i=0; i<rline.length();i++)
				{
					rchar= rline.charAt(i);
					if (isLower(rchar))
					{
						hold= ((rchar-'a'+ shiftAmount) %26);
						if (hold<0)
							hold+=26;
						strF+=(char)(hold + 'a');
					}
					else if (isUpper(rchar))
					{
						hold= ((rchar-'A' + shiftAmount)%26);
						if (hold<0)
							hold+=26;
						strF+= (char)(hold + 'A');
					}
					else
						strF+=rchar;
				}
			}
		}
		if (encrypt==false)
		{
			shiftAmount*=-1;
			while(readFrom.hasNext())
			{
				rline= readFrom.nextLine();
				for (int i=0; i<rline.length();i++)
				{
					rchar= rline.charAt(i);
					if (isLower(rchar))
					{
						hold= ((rchar-'a'+ shiftAmount) %26);
						if (hold<0)
							hold+=26;
						strF+=(char)(hold + 'a');
					}
					else if (isUpper(rchar))
					{
						hold= ((rchar-'A' + shiftAmount)%26);
						if (hold<0)
							hold+=26;
						strF+= (char)(hold + 'A');
					}
					else
						strF+=rchar;
					
				}
				
			}
		}
		return strF;
	}
	public static String perm_cipher(String fileName, boolean encrypt, char[] perm) throws IOException {
		Scanner read = new Scanner(new File(fileName));
		String currLine;
		char currChar;
		String mes = "";
		while(read.hasNext()) {
			currLine = read.nextLine();
			for(int i = 0; i < currLine.length(); i++) 
			{
				currChar = currLine.charAt(i);
				if (encrypt) 
				{
					for(int x = 0; x < perm.length; x++) 
					{
						if (perm[x] == currChar)
							mes += (char) ('a' + x);
						if (isUpper(currChar) && perm[x] == Character.toLowerCase(currChar))
							mes += (char) ('A' + x);
					}
				}
				else if (isLower(currChar)) 
				{
					mes += perm[currChar - 'a'];
				}
				else if (isUpper(currChar)) 
				{
					mes += perm[currChar - 'A'];
				}
				else 
				{ 
					mes += (currChar);
					continue;
				}
			}
			mes += "\n";
		}
		read.close();
		return mes;
	}
	public static boolean isLower(char c) 
	{
		return c>= 'a' && c <='z';
	}
	public static boolean isUpper(char c)
	{
		return c>= 'A' && c <='Z';
	}
}