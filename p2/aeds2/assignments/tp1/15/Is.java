import java.util.Scanner;

public class Is 
{
    public static void main(String[] args)
    {

        String line = MyIO.readLine();

        while(!isEqual(line, "FIM"))
        {
            MyIO.println(eval(onlyVowels(line)) + " "
             + eval(onlyConsonants(line, 0)) + " "
             + eval(isInteger(line, 0)) + " "
             + eval(isRealNumber(line, false, 0)));

            line = MyIO.readLine();
        }
    }   
    
    // Transforma um valor booleano em uma String
    public static String eval(boolean answer)
    {
        return answer ? "SIM" : "NAO";
    }

    // Verifica se uma String contém apenas vogais
    public static boolean onlyVowels(String str)
    {
        return onlyVowels(str, 0);
    }

    // Verifica se uma String contém apenas vogais de forma recursiva
    public static boolean onlyVowels(String str, int offset)
    {
        if(offset >= str.length())
            return true;

        if(!isVowel(str.charAt(offset)))
            return false;

        return onlyVowels(str, offset + 1);
    }

    // Verifica se uma String contém apenas consoantes
    public static boolean onlyConsonants(String str)
    {
        return onlyConsonants(str, 0);
    }

    // Verifica se uma String contém apenas consoantes de forma recursiva
    public static boolean onlyConsonants(String str, int offset)
    {
        if(offset >= str.length())
            return true;

        if(!isConsonant(str.charAt(offset)))
            return false;

        return onlyConsonants(str, offset + 1);
    }

    // Verifica se uma String é um número inteiro
    public static boolean isInteger(String str)
    {
        return isInteger(str, 0);
    }

    // Verifica se uma String é um número inteiro de forma recursiva
    public static boolean isInteger(String str, int offset)
    {
        if(offset >= str.length())
            return true;

        if(!isDigit(str.charAt(offset)))
            return false;

        return isInteger(str, offset  + 1);
    }

    public static boolean isRealNumber(String str)
    {
        return isRealNumber(str, false, 0);
    }

    // Verifica se uma String é um número real de forma recursiva
    public static boolean isRealNumber(String str, boolean decimalFlag, int offset)
    {
        if(offset >= str.length())
            return true;

        char c = str.charAt(offset);

        if(c == ',' || c == '.')
        {
            if(decimalFlag)
                return false;

            decimalFlag = true;
        }
        else if(!isDigit(c))
            return false;

        return isRealNumber(str, decimalFlag, offset + 1);
    }

    // Verifica se um caractere é uma vogal
    public static boolean isVowel(char c)
    {
        c = toLowerCase(c);

        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }

    // Verifica se um caractere é uma consoante
    public static boolean isConsonant(char c)
    {
        c = toLowerCase(c);

        return c > 'a' && c <= 'z' && c != 'e' && c != 'i' && c != 'o' && c != 'u'; 
    }

    // Verifica se um caractere é um dígito
    public static boolean isDigit(char c)
    {
        return c >= '0' && c <= '9';
    }

    // Transforma um caractere em sua versão minúscula se este for uma letra maiúscula
    public static char toLowerCase(char c)
    {
        if(c >= 'A' && c <= 'Z')
            return (char)(c + ('a' - 'A'));

        return c;
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
