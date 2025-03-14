public class Palindromo 
{
    public static void main(String[] args) 
    {
        MyIO.setCharset("UTF-8");
        
        String line = MyIO.readLine();

        while(!isEqual(line, "FIM"))
        {
            MyIO.println(isPalindrome(line) ? "SIM" : "NAO");

            line = MyIO.readLine();
        }
    }
    
    // Dada uma String, retorna se ela é um palíndromo ou não
    public static boolean isPalindrome(String line)
    {
        return isPalindrome(line, 0);
    }

    // Verifica se uma String é um palíndromo de forma recursiva
    public static boolean isPalindrome(String line, int pos) 
    {
        if(pos > line.length() / 2)
            return true;

        if(line.charAt(pos) != line.charAt(line.length() - 1 - pos))
            return false;

        return isPalindrome(line, pos + 1);
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