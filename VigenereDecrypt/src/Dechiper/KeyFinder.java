package Dechiper;

import java.util.Arrays;
import java.util.Collections;

public class KeyFinder {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    private static final double PT_CI = 0.074;

    private static final double EN_CI = 0.066;

    private static double CI_CHOOSE;

    private static final char PT_MOST_FREQ = 'o';

    private static final char EN_MOST_FREQ = 'e';

    private static char MOST_FREQ_CHAR_CHOOSE;

    private static boolean isPortuguese;

    private static final double MARGIN = 0.008;

    /**
     * Set the values for each language
     * @param language 0 for portuguese any other for english
     */
    public static void setLanguage(final int language) {
        if (language == 0) {
            CI_CHOOSE = PT_CI;
            MOST_FREQ_CHAR_CHOOSE = PT_MOST_FREQ;
            isPortuguese = true;
        } else {
            CI_CHOOSE = EN_CI;
            MOST_FREQ_CHAR_CHOOSE = EN_MOST_FREQ;
            isPortuguese = false;
        }
    }

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

    /**
     * Given a frequency array calculate the Coincidence Index
     * @param frequencies
     * @return
     */
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

    /**
     * Given a margin verify if the coincidence index is close to the chosen
     * @param value Some coincidence index value
     * @return True if fits the condition. False otherwise
     */
    private static boolean isCloseToCoincidenceIndex(double value) {
        double difference = value - CI_CHOOSE;
        return (difference <= MARGIN && difference > 0);
    }

    /**
     * Find the key on a cipher text
     * @param keySize The key size
     * @param text The cipher text
     * @return The Key
     */
    public static String searchKey(int keySize, String text) {
        final StringBuilder key = new StringBuilder();

        for (int i = 0; i < keySize; i++) {
            char letter = getKeyChar(keySize, i, text);
            int number = letter - MOST_FREQ_CHAR_CHOOSE;
            if (number < 0)
                number += 26;

            key.append(ALPHABET.charAt(number));
        }

        return key.toString();
    }

    /**
     * Get the key char based on text index
     * @param keySize The key size
     * @param index Start index of text
     * @param text the cipher text
     * @return The key char
     */
    private static char getKeyChar(int keySize, int index, String text) {
        int[] frequencies = new int[26];
        Arrays.fill(frequencies, 0);

        for (int k = index; k < text.length(); k += keySize) {
            int position = (int) text.charAt(k) - 97;
            frequencies[position] = frequencies[position] + 1;
        }

        return getMostFrequentChar(frequencies);
    }

    /**
     * Get the most frequent char from given array of frequencies. When language is portuguese
     * need to get the third most frequent char.
     * @param array Frequency array
     * @return The most frequent char
     */
    private static char getMostFrequentChar(int[] array) {
        if(isPortuguese){
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
        }

        int mostFrequent = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] > array[mostFrequent])
                mostFrequent = i;
        }
        return (char) (mostFrequent + 97);
    }
}
