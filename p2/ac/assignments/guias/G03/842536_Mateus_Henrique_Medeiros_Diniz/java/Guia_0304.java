/*
    Guia_0304.java
    842536 - Mateus Henrique Medeiros Diniz
*/

public class Guia_0304 {
    public static void main(String[] args) {
        System.out.println("a) 11001 (2) - 1101 (2) = " + sum("11001", c2(5, "1101", 2)));
        System.out.println("b) 101,1101 (2) - 10,01 (2) = " + setDecimal(sum("1011101", c2(7, "100100", 2)), 3));
        System.out.println("c) 312 (4) - 231 (4) = " + bin2base(sum(parseBinary("312", 4), c2(7, "231", 4)), 4));
        System.out.println("d) 376 (8) - 267 (8) = " + bin2base(sum(parseBinary("376", 8), c2(10, "146", 8)), 8));
        System.out.println("e) 7D2 (16) - A51 (16) = " + bin2base(sum(parseBinary("7d2", 16), c2(13, "6f", 16)), 16));
    }

    public static String c1(int nBits, String num, int base) {
        String bin = parseBinary(num, base);

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

        for(int i = bin1.length() - 1; i < bin1.length(); i++)
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

    public static String parseBinary(String num, int base) {
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

            sb2.insert(0, '1');

            for (int j = sb2.length(); j < Math.log(base) / Math.log(2); j++)
                sb2.insert(0, '0');

            sb.append(sb2);
        }

        sb.insert(0, '0');

        return sb.toString();
    }

    public static String bin2base(String bin, int base) {
        int groupSize = (int) (Math.log(base) / Math.log(2));

        while (bin.length() % groupSize != 0) {
            bin = "0" + bin;
        }
    
        StringBuilder sb = new StringBuilder();
    
        for (int i = 0; i < bin.length(); i += groupSize) {
            String group = bin.substring(i, i + groupSize);

            int decimalValue = Integer.parseInt(group, 2);
    
            if (decimalValue < 10) {
                sb.append((char) ('0' + decimalValue));
            } else {
                sb.append((char) ('A' + decimalValue - 10));
            }
        }
    
        return sb.toString();
    }

    public static String setDecimal(String bin, int integerCount)
    {
        StringBuilder sb = new StringBuilder(bin);

        sb.insert(integerCount, ',');

        return sb.toString();
    }
}