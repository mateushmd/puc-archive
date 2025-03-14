

public class SequencialSearch
{
    public static void main(String[] args) 
    {
        
    }

    public static boolean sequencialSearch(int[] arr, int x)
    {
        for(int i = 0; i < arr.length; i++)
        {
            if(arr[i] == x)
                return true;
        }

        return false;
    }
}