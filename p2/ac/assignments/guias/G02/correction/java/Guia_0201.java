/*
	Guia_0201.java
	842536 - Mateus Henrique Medeiros Diniz
*/

public class Guia_0201
{
    public static void main(String[] args)
    {
        int[][] binaries = {
            {0, 0, 0, 0, 0, 1, 1},
            {0, 0, 0, 1, 0, 0, 1},
            {0, 0, 1, 0, 1, 0, 1},
            {0, 1, 1, 1, 1, 0, 1},
            {1, 1, 1, 1, 0, 0, 1}};

        int[] floatingPointPositions = {1, 1, 1, 1, 2};

        for(int i = 0; i < 5; i++)
        {
            System.out.printf("%c) %f\n", 'a' + i, bin2double(binaries[i], floatingPointPositions[i]));
        }
    }

    public static double bin2double(int[] binary, int floatingPointPosition)
    {
        double x = 0;
        double power2 = Math.pow(2, floatingPointPosition);

        for(int i = 0; i < binary.length; i++)
        {
            power2 /= 2;

            if(binary[i] == 1)
                x += power2;
        }

        return x;
    }
}

/*
	Saída:
	
	a) 0,046875
	b) 0,140625
	c) 0,328125
	d) 0,953125
	e) 3,781250
*/