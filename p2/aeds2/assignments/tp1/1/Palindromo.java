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
        boolean ans = true;

        for (int i = 0; i < line.length() / 2; i++) 
        {
            int j = line.length() - 1 - i;

            if (line.charAt(i) != line.charAt(j)) 
            {
                ans = false;
                break;
            }
        }

        return ans;
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