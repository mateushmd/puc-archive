import java.util.Scanner;

public class Sort
{
    public static void main(String[] args) 
    {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        int m = in.nextInt();

        System.out.println(n + " " + m);

        while(n != 0 && m != 0)
        {
            int[] array = new int[n];

            for(int i = 0; i < n; i++)
            {
                array[i] = in.nextInt();
            }

            sort(array, m);

            print(array);

            n = in.nextInt();
            m = in.nextInt();

            System.out.println(n + " " + m);
        }

        in.close();
    }

    static void sort(int[] arr, int m)
    {
        for(int i = 0; i < arr.length; i++)
        {
            int tmp = arr[i];
            int j = i - 1;

            while((j >= 0) && check(tmp, arr[j], m))
            {
                arr[j+1] = arr[j];
                j--;
            }

            arr[j + 1] = tmp;
        }
    }

    static boolean check(int i, int j, int m)
    {
        int mi = i % m;
        int mj = j % m;

        if(mi != mj)
            return mi < mj;

        if(i % 2 != 0)
        {
            if(j % 2 != 0)
                return i > j;

            return true;
        }

        if(j % 2 == 0)
            return i < j;

        return false;
    }

    static void print(int[] arr)
    {
        for(int i = 0; i < arr.length; i++)
        {
            System.out.println(arr[i]);
        }
    }
}