import java.util.Scanner;

public class Is 
{
    public static void main(String[] args)
    {
        String line = MyIO.readLine();

        while(!isEqual(line, "FIM"))
        {
            MyIO.println(eval(onlyVowels(line)) + " "
             + eval(onlyConsonants(line)) + " "
             + eval(isInteger(line)) + " "
             + eval(isRealNumber(line)));

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
        for(int i = 0; i < str.length(); i++)
        {
            if(!isVowel(str.charAt(i)))
                return false;
        }

        return true;
    }

    // Verifica se uma String contém apenas consoantes
    public static boolean onlyConsonants(String str)
    {
        for(int i = 0; i < str.length(); i++)
        {
            if(!isConsonant(str.charAt(i)))
                return false;
        }

        return true;
    }

    // Verifica se uma String é um número inteiro
    public static boolean isInteger(String str)
    {
        for(int i = 0; i < str.length(); i++)
        {
            if(!isDigit(str.charAt(i)))
                return false;       
        }

        return true;
    }
    
    // Verifica se uma String é um número real
    public static boolean isRealNumber(String str)
    {
        boolean foundDecimalSeparator = false;

        for(int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);

            if(c == ',' || c == '.')
            {
                if(foundDecimalSeparator)
                    return false;

                foundDecimalSeparator = true;
            }
            else if(!isDigit(c))
                return false;
        }

        return true;
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
