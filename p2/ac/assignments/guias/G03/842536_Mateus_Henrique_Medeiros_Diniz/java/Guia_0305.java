/*
    Guia_0305.java
    842536 - Mateus Henrique Medeiros Diniz
*/

public class Guia_0305 {
    public static void main(String[] args) {
        System.out.println("a) 110101 (2) - 1011 (2) = " + sum("0110101", c2(7, "1011", 2)));
        System.out.println("b) 101,1001 (2) - 3,1 (8) = " + setDecimal(sum("01011001", c2(8, "110010", 2)), 4));
        System.out.println("c) 213 (4) - 3D (16) = " + sum(parseBinary("213", 4, 8), c2(8, "3d", 16)));
        System.out.println("d) C5 (16) - 1011001 (2) = " + sum(parseBinary("c5", 16, 9), c2(9, "1011001", 2)));
        System.out.println("e) 7E (16) - 2D (16) = " + sum(parseBinary("7e", 16, 8), c2(8, "2d", 16)));
    }

    public static String c1(int nBits, String num, int base) {
        String bin = base == 2 ? setBinSize(num, nBits) : parseBinary(num, base, nBits);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bin.length(); i++) {
            sb.append(bin.charAt(i) == '1' ? '0' : '1');
        }

        return sb.toString();
    }

    public static String c2(int nBits, String num, int base) {
        StringBuilder sb = new StringBuilder(c1(nBits, num, base));

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

    public static String sum(String bin1, String bin2) {
        int carry = 0;

        StringBuilder sb = new StringBuilder();

        for(int i = bin1.length() - 1; i >= 0; i--)
        {
            int b1 = Integer.parseInt(bin1.charAt(i) + "");
            int b2 = Integer.parseInt(bin2.charAt(i) + "");

            int sum = b1 + b2 + carry;

            if(sum == 1 || sum == 3)
                sb.append('1');
            else
                sb.append('0');

            carry = sum > 1 ? 1 : 0;
        }

        return sb.reverse().toString();
    }

    public static String parseBinary(String num, int base, int size) {
        num = num.toLowerCase();

        StringBuilder sb = new StringBuilder();

        for (char digit : num.toCharArray()) {
            StringBuilder sb2 = new StringBuilder();

            int value;

            if (digit >= '0' && digit <= '9')
                value = digit - '0';
            else
                value = digit - 'a' + 10;

            while (value >= 2) {
                sb2.insert(0, value % 2);
                value /= 2;
            }

            sb2.insert(0, value);

            for (int j = sb2.length(); j < Math.log(base) / Math.log(2); j++)
                sb2.insert(0, '0');

            sb.append(sb2);
        }

        return setBinSize(sb.toString(), size);
    }

    public static String bin2base(String bin, int base) {
        int groupSize = (int) (Math.log(base) / Math.log(2));

        StringBuilder sb = new StringBuilder();

        for (int i = bin.length() - 1; i >= 1; i -= groupSize) {
            int endIndex = Math.max(i - groupSize + 1, 1);

            StringBuilder group = new StringBuilder(bin.substring(endIndex, i + 1));

            int decimalValue = Integer.parseInt(group.toString(), 2);

            if (decimalValue < 10) {
                sb.append((char) ('0' + decimalValue));
            } else {
                sb.append((char) ('A' + decimalValue - 10));
            }
        }

        sb.reverse();

        if(bin.charAt(0) == '1')
            sb.insert(0, '-');

        return sb.toString();
    }

    public static String setDecimal(String bin, int integerCount)
    {
        StringBuilder sb = new StringBuilder(bin);

        sb.insert(integerCount, ',');

        return sb.toString();
    }

    public static String setBinSize(String bin, int size)
    {
        StringBuilder sb = new StringBuilder(bin);

        for(int i = bin.length(); i < size; i++)
        {
            sb.insert(0, '0');
        }

        return sb.toString();
    }
}