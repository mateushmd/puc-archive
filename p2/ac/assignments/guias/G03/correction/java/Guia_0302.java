/*
	Guia_0302.java
	842536 - Mateus Henrique Medeiros Diniz
*/

public class Guia_0302 {
    public static void main(String[] args) {
        System.out.println("a) C(1,6) 321 (4) = " + c1(6, "321", 4));
        System.out.println("b) C(1,8) b2 (16) = " + c1(8, "b2", 16));
        System.out.println("c) C(2,6) 231 (4) = " + c2(6, "231", 4));
        System.out.println("d) C(2,10) 146 (8) = " + c2(10, "146", 8));
        System.out.println("e) C(2,8) 6f (16) = " + c2(8, "6f", 16));
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

        return sb.toString();
    }
}