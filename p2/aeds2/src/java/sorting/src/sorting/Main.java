package sorting;

import java.util.ArrayList;
import java.util.Collections;

import sorting.lib.*;

public class Main 
{
    private static final int SIZE = 100;
    private static final int ITERATIONS = 1;

    public static void main(String[] args)
    {
        for(int i = 0; i < ITERATIONS; i++)
        {
            int[] array = generate();
            print(array);
            RadixSort.sort(array);
            print(array);
            logSorted(array);
            System.out.println();
        }
    }

    public static int[] generate()
    {
        ArrayList<Integer> list = new ArrayList<>();

        for(int i = 0; i < SIZE; i++)
        {
            list.add(i);
        }

        Collections.shuffle(list);

        int[] arr = new int[SIZE];

        for(int i = 0; i < SIZE; i++)
        {
            arr[i] = list.get(i);
        }

        return arr;
    }

    public static void print(int[] arr)
    {
        for(int i = 0; i < arr.length; i++)
            System.out.print(arr[i] + " ");

        System.out.println();
    }
    
    public static void logSorted(int[] arr)
    {
        boolean check = true; 

        for(int i = 0; i < arr.length - 1; i++)
        {
            if(arr[i] > arr[i + 1])
            {
                check = false;
                break;
            }
        }

        System.out.println("sorted? " + check);
    }
}
