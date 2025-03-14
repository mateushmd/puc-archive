package sorting.lib;

/*
    melhor caso: n^2
    pior caso: n^2
    algoritmo não estável
*/
public class SelectionSort extends Sorting
{
    public static void sort(int[] arr)
    {
        for(int i = 0; i < arr.length - 1; i++)
        {
            int smallest = i;

            for(int j = i + 1; j < arr.length; j++)
            {
                if(arr[j] < arr[smallest])
                    smallest = j;
            }

            swap(arr, i, smallest);
        }
    }
}