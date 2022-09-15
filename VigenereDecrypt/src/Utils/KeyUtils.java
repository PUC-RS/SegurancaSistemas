package Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class KeyUtils {

    private static final int MAX_KEY_LENGTH = 30;
    private static final ArrayList<String> keysPt = new ArrayList<>();
    private static final ArrayList<String> keysEn = new ArrayList<>();

    private static final HashMap<Integer, Double> coincidenceIndexPt = new HashMap<>();

    private static final HashMap<Integer, Double> coincidenceIndexEn = new HashMap<>();

    private static final HashMap<Character, Integer> ALPHABET = new Alphabet().getALPHABET();

    private KeyUtils() {
    }

    public static ArrayList<String> getKeysPt() {
        return keysPt;
    }

    public static ArrayList<String> getKeysEn() {
        return keysEn;
    }

    public static void findKeys(final String encryptedText) {
        calculateCoincidenceIndex(encryptedText);
    }

    private static void calculateCoincidenceIndex(final String encryptedText) {
        for (int keySize = 0; keySize < MAX_KEY_LENGTH; keySize++) {
            ArrayList<String> encryptedSplitText = splitText(encryptedText, keySize);

            ArrayList<HashMap<Character, Integer>> frequencies = calculateFrequency(encryptedSplitText);

            for (final HashMap<Character, Integer> frequency : frequencies) {
                final long subStringSize = frequency.remove('/');
                final long n = subStringSize * (subStringSize - 1);
                long sum = 0;

                for (final char c : frequency.keySet()) {
                    final long letterFrequency = frequency.get(c);
                    sum += letterFrequency * (letterFrequency - 1);
                }

                final double coincidenceIndex = (double) sum / n;

                if (coincidenceIndex > 0.062 && coincidenceIndex < 0.07) {
                    coincidenceIndexEn.put(keySize, coincidenceIndex);
                } else if (coincidenceIndex > 0.07 && coincidenceIndex < 0.078) {
                    coincidenceIndexPt.put(keySize, coincidenceIndex);
                }
            }
        }
    }

    private static ArrayList<String> splitText(final String encryptedText, final int keySize) {
        final ArrayList<String> textSplit = new ArrayList<>();
        StringBuilder builder;

        for (int i = 0; i < keySize; i++) {
            builder = new StringBuilder();
            for (int j = i; j < encryptedText.length(); j += keySize) {
                builder.append(encryptedText.charAt(j));
            }
            textSplit.add(builder.toString());
        }

        return textSplit;
    }

    private static ArrayList<HashMap<Character, Integer>> calculateFrequency(final ArrayList<String> slipText) {
        final ArrayList<HashMap<Character, Integer>> frequencies = new ArrayList<>();
        final HashMap<Character, Integer> analysis = new HashMap<>();
        for (final String aux : slipText) {
            analysis.clear();
            analysis.putIfAbsent('/', aux.length());

            for (int i = 0; i < aux.length(); i++) {
                final char letter = aux.charAt(i);
                if (analysis.containsKey(letter)) {
                    analysis.put(letter, analysis.get(letter) + 1);
                } else {
                    analysis.putIfAbsent(letter, 1);
                }
            }
            frequencies.add(analysis);
        }

        return frequencies;
    }


}
