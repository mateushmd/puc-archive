import java.util.EmptyStackException;
import java.util.Scanner;
import java.util.Stack;

public class Balanco
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);

        String line = in.nextLine();

        while(!line.equals("FIM"))
        {
            System.out.println(balance(line) ? "correto" : "incorreto");

            line = in.nextLine();
        }

        in.close();
    }

    public static boolean balance(String expr)
    {
        Stack<Character> stack = new Stack<>();

        for(char c : expr.toCharArray())
        {
            if(c == '(')
                stack.add(c);
            
            else if(c == ')')
            {
                try
                {
                    c = stack.pop();
                }
                catch(EmptyStackException e)
                {
                    return false;
                }
            }
        }

        return stack.size() == 0; 
    }
}