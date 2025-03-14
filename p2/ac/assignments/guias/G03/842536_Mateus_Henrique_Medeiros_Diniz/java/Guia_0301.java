/*
	Guia_0301.java
	842536 - Mateus Henrique Medeiros Diniz
*/

public class Guia_0301 {
    public static void main(String[] args) {
        System.out.println("a) C(1,6) 1010 = " + c1(6, "1010"));
        System.out.println("b) C(1,8) 1101 = " + c1(8, "1101"));
        System.out.println("c) C(2,6) 101001 = " + c2(6, "101001"));
        System.out.println("d) C(2,7) 101111 = " + c2(7, "101111"));
        System.out.println("e) C(2,8) 110100 = " + c2(8, "110100"));
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
}