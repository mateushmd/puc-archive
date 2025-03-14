public class MedianOfThreePivotStrategy implements IPivotStrategy 
{
    @Override
    public int execute(int[] array, int left, int right) 
    {
        int first = array[left];
        int mid = array[(left + right) / 2];
        int last = array[right];	

        if((first > mid) != (first > last)) return first;
        if((mid > first) != (mid > last)) return mid;
        return last;
    }    
}
