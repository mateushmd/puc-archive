import java.util.Random;

public class RandomPivotStrategy implements IPivotStrategy
{
    private Random random;

    public RandomPivotStrategy()
    {
        random = new Random();
    }

    @Override
    public int execute(int[] array, int left, int right) 
    {
        random.setSeed(System.currentTimeMillis());

        return array[left + random.nextInt(right - left + 1)];
    }
}