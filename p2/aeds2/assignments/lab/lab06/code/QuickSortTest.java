import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class QuickSortTest 
{
    static final int TEST_AMMOUNT = 10;

    public static void main(String[] args)
    {
        Map<Integer, List<List<Double>>> firstPivotTestMap = createTestMap();
        Map<Integer, List<List<Double>>> lastPivotTestMap = createTestMap();
        Map<Integer, List<List<Double>>> medianOfThreePivotTestMap = createTestMap();
        Map<Integer, List<List<Double>>> randomPivotTestMap = createTestMap();

        test(firstPivotTestMap, new FirstPivotStrategy());
        test(lastPivotTestMap, new LastPivotStrategy());
        test(medianOfThreePivotTestMap, new MedianOfThreePivotStrategy());
        test(randomPivotTestMap, new RandomPivotStrategy());

        System.out.println("----- Médias de Execução -----");
        System.out.println("Primeiro Pivô:");
        calculateAverageTimes(firstPivotTestMap);
        System.out.println("\nÚltimo Pivô:");
        calculateAverageTimes(lastPivotTestMap);
        System.out.println("\nMediana de Três Pivôs:");
        calculateAverageTimes(medianOfThreePivotTestMap);
        System.out.println("\nPivô Aleatório:");
        calculateAverageTimes(randomPivotTestMap);
    }

    static void calculateAverageTimes(Map<Integer, List<List<Double>>> testMap)
    {
        for (int size : testMap.keySet())
        {
            List<Double> orderedArrayTimes = testMap.get(size).get(0);
            List<Double> almostOrderedArrayTimes = testMap.get(size).get(1);
            List<Double> randomArrayTimes = testMap.get(size).get(2);

            System.out.printf("Tamanho %d:\n", size);
            System.out.printf(" - Arrays Ordenados: %.3f ms\n", calculateAverage(orderedArrayTimes));
            System.out.printf(" - Arrays Quase Ordenados: %.3f ms\n", calculateAverage(almostOrderedArrayTimes));
            System.out.printf(" - Arrays Aleatórios: %.3f ms\n", calculateAverage(randomArrayTimes));
        }
    }

    static double calculateAverage(List<Double> times)
    {
        double sum = 0;
        for (double time : times)
        {
            sum += time;
        }
        return sum / times.size();
    }

    static Map<Integer, List<List<Double>>> createTestMap()
    {
        Map<Integer, List<List<Double>>> map = new HashMap<>();

        for(int key : new int[]{100, 1000, 10000})
        {
            List<List<Double>> listOfLists = new ArrayList<>();

            for(int i = 0; i < 3; listOfLists.add(new ArrayList<>()), i++);

            map.put(key, listOfLists);
        }

        return map;
    }

    static int[] generateOrderedArray(int size)
    {
        int[] array = new int[size];

        for(int i = 0; i < size; i++)
        {
            array[i] = i;
        }

        return array;
    }

    static int[] generateAlmostOrderedArray(int size)
    {
        int[] array = generateOrderedArray(size);

        Random random = new Random(System.currentTimeMillis());

        int swapCount = size / 10;

        for(int i = 0; i < swapCount; i++)
        {
            int index1 = random.nextInt(size);
            int index2 = random.nextInt(size);

            int tmp = array[index1];
            array[index1] = array[index2];
            array[index2] = tmp;
        }

        return array;
    }

    static int[] generateRandomArray(int size)
    {
        Random random = new Random(System.currentTimeMillis());

        int[] array = new int[size];

        for(int i = 0; i < size; i++)
        {
            array[i] = random.nextInt(size);
        }

        return array;
    }

    static void printArray(int[] array)
    {
        System.out.print("[");

        for(int i = 0; i < array.length; i++)
        {
            System.out.print(array[i]);

            if(i < array.length - 1)
                System.out.print(", ");
        }

        System.out.println("]");
    }

    static void test(Map<Integer, List<List<Double>>> testMap, IPivotStrategy pivotStrategy)
    {
        QuickSort quickSort = new QuickSort(pivotStrategy);

        for(int size : testMap.keySet())
        {
            List<Double> orderedArrayExecutionTimes = testMap.get(size).get(0);
            List<Double> almostOrderedArrayExecutionTimes = testMap.get(size).get(1);
            List<Double> randomArrayExecutionTimes = testMap.get(size).get(2);
            
            for(int i = 0; i < TEST_AMMOUNT; i++)
            {
                int[] orderedArray = generateOrderedArray(size);
                int[] almostOrderedArray = generateAlmostOrderedArray(size);
                int[] randomArray = generateRandomArray(size);

                orderedArrayExecutionTimes.add(execute(quickSort, orderedArray));
                almostOrderedArrayExecutionTimes.add(execute(quickSort, almostOrderedArray));
                randomArrayExecutionTimes.add(execute(quickSort, randomArray));
            }
        }
    }

    static double execute(QuickSort quickSort, int[] array)
    {
        long time = System.nanoTime();
        quickSort.sort(array);
        long delta = System.nanoTime() - time;
        
        double ms = (double)(delta) / 1000000;

        return ms;
    }
}
