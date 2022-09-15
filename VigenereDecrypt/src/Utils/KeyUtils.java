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
        for (int keySize = 0; keySize < MAX_KEY_LENGTH; keySize++) {
            ArrayList<String> encryptedSplitText = splitText(encryptedText, keySize);
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
}
