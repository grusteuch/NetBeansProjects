package sequence;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * The Sequence class implements an application that
 * finds the first occurrence of subsequence.
 */
public class Sequence {

    private static final int UNDEFINED = -1;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        while (reader.hasNextLine()) {
            String subsequence = reader.nextLine();
            BigInteger minNumber = null;
            int minT = UNDEFINED;
            int length = subsequence.length();
            for (int i = 1; i <= length; i++) {
                if (minNumber != null) {
                    break;
                }
                for (int t = 0; t < i; t++) {
                    if (subsequence.charAt(t) == '0') {
                        continue;
                    }
                    BigInteger num = new BigInteger(subsequence.substring(t, i));
                    String p1 = subsequence.substring(0, t);
                    if (p1.length() > 0 && p1.replace("9", "").length() == 0) {
                        num = num.subtract(BigInteger.ONE);
                    }
                    String p2 = String.valueOf(num);
                    String pattern = p2 + p1;
                    BigInteger number = new BigInteger(pattern);
                    BigInteger temp = new BigInteger(pattern);
                    while (pattern.length() < length*2) {
                        temp = temp.add(BigInteger.ONE);
                        pattern += temp;
                    }
                    int j = t == 0 ? 0 : i - t;
                    if (pattern.substring(j, j+length).contains(subsequence)) {
                        if (minNumber == null || minNumber.compareTo(number) > 0) {
                            minNumber = number;
                            minT = t;
                        }
                    }
                }
            }
            System.out.println(position(minNumber, minT));
        }
    }
    
    private static BigInteger position(BigInteger number, int t) {
        int d = String.valueOf(number).length();
        BigInteger position = BigInteger.valueOf(0);
        BigInteger k = BigInteger.valueOf(0);
        for (int i = 0; i < d-1; i++) {
            BigInteger a = BigInteger.valueOf(9).multiply(BigInteger.valueOf(10).pow(i));
            k = k.add(a);
            position = position.add(a.multiply(BigInteger.valueOf(i+1)));
        }
        position = position.add(BigInteger.ONE).add(number.subtract(k.add(BigInteger.ONE)).multiply(BigInteger.valueOf(d)));
        if (t > 0) {
            return position.add(BigInteger.valueOf(d)).subtract(BigInteger.valueOf(t));
        } else {
            return position;
        }
    }
    
}