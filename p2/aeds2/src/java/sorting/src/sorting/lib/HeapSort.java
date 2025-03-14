package sorting.lib;

public class HeapSort extends Sorting 
{
    public static void sort(int[] arr)
    {
        for(int i = 1; i < arr.length; i++)
        {
            build(arr, i);
        }
        int s = arr.length - 1;
        while(s > 0)
        {
            swap(arr, 0, s--);
            rebuild(arr, s);
        }
    }    

    public static void build(int[] arr, int s)
    {
        for(int i = s; arr[i] > arr[(i - 1) / 2] && i >= 1; i /= 2)
            swap(arr, i, (i - 1) / 2);
    }

    public static void rebuild(int[] arr, int s)
    {
        int i = 0;
        while(hasChildren(i, s))
        {
            int child = getBiggestChildPosition(arr, i, s);

            if(arr[i] < arr[child])
            {
                swap(arr, i, child);
                i = child;
            }
            else
                i = s;  
        }
    }

    public static boolean hasChildren(int i, int s)
    {
        return i * 2 < s;
    }

    public static int getBiggestChildPosition(int[] arr, int i, int s)
    {
        int c1 = i * 2 + 1;
        int c2 = i * 2 + 2;

        if(c2 > s)
            return c1;

        return arr[c1] > arr[c2] ? c1 : c2;
    }
}
