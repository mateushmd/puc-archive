/*
	Guia_0303.java
	842536 - Mateus Henrique Medeiros Diniz
*/

public class Guia_0303
{
    public static void main(String[] args)
    {
        String[] bins = new String[]{"10110", "110011", "100100", "1011011", "1110011"};

        for(int i = 0; i < bins.length; i++)
        {
            System.out.printf("%c) C(2,%d) %s (2) = %d (10)\n", 'a' + i, bins[i].length(), bins[i], sbin2dec(bins[i]));
        }
    }

    public static int sbin2dec(String sbin)
    {
        return bin2dec(c2(sbin.length(), sbin));
    }

    public static String c1(int nBits, String bin) {
        StringBuilder sb = new StringBuilder();

        int remainingBits = nBits - bin.length();

        for (int i = 0; i < bin.length(); i++) {
            sb.append(bin.charAt(i) == '1' ? '0' : '1');
        }

        for (int i = 0; i < remainingBits; i++) {
            sb.insert(0, '1');
        }

        return sb.toString();
    }

    public static String c2(int nBits, String bin) {
        StringBuilder sb = new StringBuilder(c1(nBits, bin));

        int i = sb.length() - 1;
        boolean carry;

        do {
            char bit;

            if (sb.charAt(i) == '1') {
                bit = '0';
                carry = true;
            } else {
                bit = '1';
                carry = false;
            }

            sb.replace(i, i + 1, String.valueOf(bit));
            i--;
        } while (carry && i > -1);

        return sb.toString();
    }

    public static int bin2dec(String bin)
    {
        int num = 0;
        int pow = 1;

        for(int i = bin.length() - 1; i >= 0; i--)
        {
            if(bin.charAt(i) == '1')
                num += pow;

            pow *= 2;
        }

        return num;
    }
}
