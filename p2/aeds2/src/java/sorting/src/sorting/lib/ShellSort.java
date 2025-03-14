package sorting.lib;

public class ShellSort extends Sorting
{
    public static void sort(int[] arr)
    {
        int h = 1;
        do { h = (h * 3) + 1; } while(h < arr.length);
        do
        {
            h /= 3;

            for(int color = 0; color < h; color ++)
                insertionSort(arr, color, h);
        } while(h != 1);
    }

    private static void insertionSort(int[] arr, int color, int h)
    {
        for(int i = (h + color); i < arr.length; i += h)
        {
            int tmp = arr[i];
            int j = i - h;

            while(j >= 0 && tmp < arr[j])
            {
                arr[j + h] = arr[j];
                j -= h;
            }

            arr[j + h] = tmp;
        }
    }
}