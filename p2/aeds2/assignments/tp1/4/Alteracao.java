import java.util.Scanner;
import java.util.Random;

public class Alteracao 
{
    public static void main(String args[])
    {
        Random rand = new Random();
        
        rand.setSeed(4);

        String line = MyIO.readLine();

        while(!isEqual(line, "FIM"))
        {
            String answer = randomReplace(line, (char)('a' + (Math.abs(rand.nextInt() % 26))), (char)('a' + (Math.abs(rand.nextInt() % 26))));

            MyIO.println(answer);

            line = MyIO.readLine();
        }
    }

    // Dada uma String, substitui todas as ocorrências de "target" pelo caractere "replace"
    public static String randomReplace(String str, char target, char replace)
    {
        String replaced = "";

        for(int i = 0; i < str.length(); i++)
        {
            if(str.charAt(i) == target)
                replaced += replace;
            else
                replaced += str.charAt(i);
        }

        return replaced;
    }

    // Compara duas Strings e retorna se a primeira é igual à segunda
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
