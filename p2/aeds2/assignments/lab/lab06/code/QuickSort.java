public class QuickSort 
{
    private IPivotStrategy pivotStrategy;

    public QuickSort(IPivotStrategy pivotStrategy)
    {
        this.pivotStrategy = pivotStrategy;
    }

    public void sort(int[] array)
    {
        sort(array, 0, array.length - 1);
    }

    private void sort(int[] array, int left, int right)
    {
        int i = left, j = right;
        int pivot = pivotStrategy.execute(array, left, right);

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

        if(j > left)
            sort(array, left, j);
        if(i < right)
            sort(array, i, right);
    }

    private void swap(int[] array, int i, int j)
    {
        int tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }

    public void setPivotStrategy(IPivotStrategy pivotStrategy)
    {
        this.pivotStrategy = pivotStrategy;
    }
}
