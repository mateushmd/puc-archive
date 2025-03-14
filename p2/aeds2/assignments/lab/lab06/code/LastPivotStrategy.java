public class LastPivotStrategy implements IPivotStrategy
{
    @Override
    public int execute(int[] array, int left, int right) 
    {
        return array[right];
    }
}
