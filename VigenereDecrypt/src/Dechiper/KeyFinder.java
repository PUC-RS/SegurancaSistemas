package Dechiper;

import java.util.Arrays;
import java.util.Collections;

public class KeyFinder {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    private static final double PT_CI = 0.074;

    private static final double EN_CI = 0.066;

    private static final char PT_MOST_FREQ = 'o';

    private static final char EN_MOST_FREQ = 'e';

    private static final double MARGIN = 0.008;

    /**
     * Find the key size on a given text
     *
     * @param text The text cipher with Vigenere
     * @return keySize  The key size (between 1 and 30)
     */
    public static int searchKeySize(String text) {
        for (int i = 1; i <= 30; i++) {
            for (int j = 0; j < i; j++) {
                int[] frequencies = new int[26];
                Arrays.fill(frequencies, 0);

                for (int k = j; k < text.length(); k += i) {
                    int position = (int) text.charAt(k) - 97;
                    frequencies[position] = frequencies[position] + 1;
                }
                double index = calculateCoincidenceIndex(frequencies);

                if (isCloseToCoincidenceIndex(index))
                    return i;
            }
        }

        return 0;
    }

    private static double calculateCoincidenceIndex(int[] frequencies) {
        long sum = 0;
        long textLength = 0;
        for (int i = 0; i < frequencies.length; i++) {
            long frequency = frequencies[i];
            if (frequency > 0) {
                sum += frequency * (frequency - 1);
                textLength += frequency;
            }
        }
        return sum / (double) (textLength * (textLength - 1));
    }

    private static boolean isCloseToCoincidenceIndex(double value) {
        double difference = value - PT_CI;
        return (difference <= MARGIN && difference > 0);
    }

    public static String searchKey(int keySize, String text) {
        final StringBuilder key = new StringBuilder();

        for (int i = 0; i < keySize; i++) {
            char letter = getKeyChar(keySize, i, text);
            int number = letter - PT_MOST_FREQ;
            if (number < 0)
                number += 26;

            key.append(ALPHABET.charAt(number));
        }

        return key.toString();
    }

    private static char getKeyChar(int number, int index, String text) {
        int[] frequencies = new int[26];
        Arrays.fill(frequencies, 0);

        for (int k = index; k < text.length(); k += number) {
            int position = (int) text.charAt(k) - 97;
            frequencies[position] = frequencies[position] + 1;
        }

        return getMostFrequentChar(frequencies);
    }

    private static char getMostFrequentChar(int[] array) {
//        int mostFrequent = 0;
//        for (int i = 0; i < array.length; i++) {
//            if (array[i] > array[mostFrequent])
//                mostFrequent = i;
//        }
//        return (char) (mostFrequent + 97);
        int[] copyOfArray = new int[26];
        System.arraycopy(array, 0, copyOfArray, 0, 26);
        int size = copyOfArray.length;
        Arrays.sort(copyOfArray);
        int third = copyOfArray[size - 3];
        for (int i = 0; i < size; i++) {
            if (array[i] == third) {
                return (char) (i + 97);
            }
        }

//        int[] array = new int[26];
//        System.arraycopy(alphabet, 0, array, 0, 26);
//        int size = array.length;
//        Arrays.sort(array);
//
//        int first = array[size - 3];
//        for (int i = 0; i < alphabet.length; i++) {
//            if (first == alphabet[i]) {
//                return (char) (i + 97);
//            }
//        }
//    }
        return 0;
    }
}
