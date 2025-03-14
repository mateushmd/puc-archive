import java.util.Scanner;

public class Ciframento 
{
    public static void main(String args[])
    {
        String line = MyIO.readLine();

        while(!isEqual(line, "FIM"))
        {
            MyIO.println(cipher(line));

            line = MyIO.readLine();
        } 
    }

    // Dada uma String, retorna sua versão cifrada
    public static String cipher(String str)
    {
        String ciphered = "";

        for(int i = 0; i < str.length(); i++)
        {
            ciphered += (char)(str.charAt(i) + 3);
        }

        return ciphered;
    }

    //Compara duas Strings e retorna se a primeira é igual à segunda
    public static boolean isEqual(String str1, String str2)
    {
        for(int i = 0; i < str1.length(); i++)
        {
            if(i >= str2.length())
                return false;

            if(str1.charAt(i) != str2.charAt(i))
                return false;
        }

        return true;
    }
}
