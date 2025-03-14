package sorting.lib;

public class RadixSort extends Sorting
{
    public static void sort(int[] arr)
    {
        int maior = arr[0];

        for(int i = 1; i < arr.length; i++)
            if(arr[i] > maior) maior = arr[i];

        for(int exp = 1; maior / exp > 0; exp *= 10)
        {
            countingSort(arr, exp);
        }
    }

    static void countingSort(int[] arr, int exp)
    {
        int[] resp = new int[arr.length];
        int[] count = new int[10];

        for(int i = 0; i < arr.length; count[(arr[i] / exp) % 10]++, i++);

        for(int i = 1; i < 10; count[i] += count[i - 1], i++);

        for(int i = arr.length - 1; i >= 0; resp[count[(arr[i] / exp) % 10] - 1] = arr[i], count[(arr[i] / exp) % 10]--, i--);

        for(int i = 0; i < arr.length; arr[i] = resp[i], i++);
    }
}
