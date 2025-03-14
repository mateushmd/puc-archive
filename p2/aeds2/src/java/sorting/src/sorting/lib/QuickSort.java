package sorting.lib;

/*
    comparacoes:
        melhor caso: n lg n
        pior caso: n^2
    movimentacoes:
        melhor caso: 1
        pior caso: n lg n
    algoritmo não estável
*/

public class QuickSort extends Sorting 
{
    public static void sort(int[] array)
    {
        quickSort(array, 0, array.length - 1);
    }

    private static void quickSort(int[] array, int l, int r)
    {
        int i = l, j = r, pivot = array[(r + l) / 2];

        while(i <= j)
        {
            while(array[i] < pivot)
                i++;

            while(array[j] > pivot)
                j--;

            if(i <= j)
            {
                swap(array, i, j);
                i++;
                j--;
            }
        }

        if(j > l)
            quickSort(array, l, j);

        if(i < r)
            quickSort(array, i, r);
    }
}
