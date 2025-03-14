import java.util.Scanner;

public class Sequencia
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);

        while(in.hasNextInt())
        {
            int n1 = in.nextInt();
            int n2 = in.nextInt();

            for(int i = n1; i <= n2; i++)
            {
                System.out.print(i);
            }
            for(int i = n2; i >= n1; i--)
            {
                System.out.print(reverse(i));
            }
            System.out.println();
        }

        in.close();
    }

    public static String reverse(int n)
    {
        StringBuilder sb = new StringBuilder(String.valueOf(n));

        return sb.reverse().toString();
    }
}