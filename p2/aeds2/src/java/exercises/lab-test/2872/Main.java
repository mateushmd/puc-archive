import java.util.Scanner;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args) 
    {
        Scanner in = new Scanner(System.in);

        boolean pad = false;
        
        while(in.hasNext())
        {
            in.nextInt();

            int n;
            ArrayList<Integer> list = new ArrayList<>();

            if(pad)
                System.out.println();

            while(!in.hasNextInt())
            {
                in.next();
                n = in.nextInt();

                list.add(n);

                int j = list.size() - 2;

                while(j >= 0 && list.get(j) > n)
                {
                    list.set(j + 1, list.get(j));
                    j--;
                }

                list.set(j + 1, n);
            }

            in.nextInt();

            for(Integer i : list)
            {
                System.out.print("Package ");
                String num = "" + i;
                while(num.length() < 3)
                {
                    num = "0" + num;
                }
                System.out.println(num);
            }
            
            pad = true;
        }

        in.close();
    }
}