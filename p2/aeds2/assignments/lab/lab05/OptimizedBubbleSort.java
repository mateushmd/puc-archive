import java.util.ArrayList;
import java.util.Collections;

public class OptimizedBubbleSort
{
    public static void main(String[] args)
    {
        int[] arr = generateArr();

        printArr(arr);

        bubbleSort(arr);

        printArr(arr);
    }

    public static int[] generateArr()
    {
        ArrayList<Integer> numbers = new ArrayList<>();

        for(int i = 0; i < 10; i++)
            numbers.add(i);

        Collections.shuffle(numbers);

        int[] arr = new int[10];

        for(int i = 0; i < 10; i++)
            arr[i] = numbers.get(i);

        return arr;
    }

    public static void bubbleSort(int[] arr)
    {
        int n = arr.length;

        boolean swapped = false;

        int pos = 0;

        for(int i = 0; i < n - 1; i++)
        {
            for(int j = pos; j < n - i - 1; j++)
            {
                if(arr[j] > arr[j + 1])
                {
                    if(!swapped) pos = j;
                    swap(arr, j, j + 1);
                    swapped = true;
                }
            }

            if(!swapped)
                break;
        }
    }

    public static void swap(int[] arr, int i, int j)
    {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void printArr(int[] arr)
    {
        for(int i = 0; i < arr.length; i++)
        {
            System.out.print(arr[i] + " ");
        }

        System.out.println();
    }
}