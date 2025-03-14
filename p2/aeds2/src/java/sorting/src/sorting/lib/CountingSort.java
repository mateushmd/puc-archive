package sorting.lib;

public class CountingSort extends Sorting
{

    /*
        melhor caso: n
        pior caso: n
        algoritmo não estável
    */

    public static void sort(int[] array)
    {
        int biggest = array[0], n = array.length;

        for(int i = 1; i < n; biggest = biggest < array[i] ? array[i] : biggest, i++);
        
        int[] sorted = new int[n];
        int[] count = new int[biggest + 1];
        int n2 = count.length;

        for(int i = 0; i < n2; count[i] = 0, i++);

        for(int i = 0; i < n; count[array[i]]++, i++);

        for(int i = 1; i < n2; count[i] += count[i - 1], i++);

        for(int i = 0; i < n; sorted[count[array[i]] - 1] = array[i], count[array[i]]--, i++);

        for(int i = 0; i < n; array[i] = sorted[i], i++);
    }    
}
