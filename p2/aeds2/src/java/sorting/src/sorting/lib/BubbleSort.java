package sorting.lib;

public class BubbleSort extends Sorting
{
    public static void sort(int[] arr)
    {
        for(int i = 0; i < arr.length - 1; i++)
        {
            boolean swapped = false;

            for(int j = 0; j < arr.length - i - 1; j++)
            {
                if(arr[j] > arr[j + 1])
                {
                    swap(arr, j, j + 1);
                    swapped = true;
                }
            }

            if(!swapped)
                return;
        }
    }
}