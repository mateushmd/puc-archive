package sorting.lib;

public class MergeSort extends Sorting 
{
    public static void sort(int[] arr)
    {
        mergeSort(arr, 0, arr.length - 1);
    }   
    
    private static void mergeSort(int[] arr, int l, int r)
    {
        if(l < r)
        {
            int m = (l + r) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    private static void merge(int[] arr, int l, int m, int r)
    {
        int tL = (m + 1) - l;
        int tR = r - m;

        int[] aL = new int[tL + 1];
        int[] aR = new int[tR + 1];

        aL[tL] = aR[tR] = 0x7FFFFFFF;
        int iL, iR, i;

        for(iL = 0; iL < tL; iL++) aL[iL] = arr[l + iL];
        for(iR = 0; iR < tR; iR++) aR[iR] = arr[m + 1 + iR];

        for(iL = iR = 0, i = l; i <= r; i++)
            arr[i] = aL[iL] <= aR[iR] ? aL[iL++] : aR[iR++];
    }
}
