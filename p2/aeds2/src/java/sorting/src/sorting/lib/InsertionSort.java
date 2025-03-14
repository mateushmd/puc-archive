package sorting.lib;

/*
    melhor caso: n
    pior caso: n^2
    algoritmo estável
*/

public class InsertionSort extends Sorting
{
    public static void sort(int[] arr)
    {
        for(int i = 1; i < arr.length; i++)
        {
            int temp = arr[i];
            int j = i - 1;

            while(j >= 0 && temp < arr[j])
            {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = temp;
        }
    }
}