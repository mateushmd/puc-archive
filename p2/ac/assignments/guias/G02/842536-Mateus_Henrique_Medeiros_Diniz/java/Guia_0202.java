public class Guia_0202
{
    public static void main(String[] args)
    {
        double[] nums = new double[]{0.375, 2.125, 3.625, 5.03125, 6.75};

        for(int i = 0; i < 5; i++)
        {
            System.out.printf("%c) ", 'a' + i);

            int intPart = (int) Math.floor(nums[i]);

            boolean[] intPartBin = int2bin(intPart);
            boolean[] realPartBin = double2bin(nums[i] - intPart);

            for(int j = 0; j < 3; j++)
            {
                System.out.print(intPartBin[j] ? '1' : '0');
            }

            System.out.print(",");

            for(int j = 0; j < 8; j++)
            {
                System.out.print(realPartBin[j] ? '1' : '0');
            }

            System.out.println(" (2)");
        }
    }

    public static boolean[] int2bin(int num)
    {
        boolean[] bin = new boolean[3];

        int i = 2;

        while(num > 0 && i >= 0)
        {
            bin[i] = num % 2 != 0;
            num /= 2;

            i--;
        }

        return bin;
    }

    public static boolean[] double2bin(double num)
    {
        boolean[] bin = new boolean[8];

        for(int i = 0; i < 7; i++)
        {
            bin[i] = false;
        }

        int i = 0;

        while(num > 0 && i < 8)
        {
            num *= 2;

            if(num >= 1)
            {
                num -= 1;
                bin[i] = true;
            }
            else
            {
                bin[i] = false;
            }

            i++;
        }

        return bin;
    }
}