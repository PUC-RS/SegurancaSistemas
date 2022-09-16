package Decrypter;

import Utils.Alphabet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VereneDecrypt {
    public static String decipherText(final String cipherText, final String key) {
        final HashMap<Character, Integer> ALPHABET = new Alphabet().getALPHABET();
        final StringBuilder plainText = new StringBuilder();
        int[] letterShift = new int[key.length()];

        for (int i = 0; i < key.length(); i++) {
            letterShift[i] = ALPHABET.get(key.charAt(i));
        }

        final ArrayList<String> cipherTextSplit = splitText(key.length(), cipherText);
        final ArrayList<String> plainTextSplit = new ArrayList<>();
        String aux = "";

        for (int i = 0; i < cipherTextSplit.size(); i++) {
            final StringBuilder plainTextBuilder = new StringBuilder();
            aux = cipherTextSplit.get(i);
            for (int j = 0; j < aux.length(); j++) {
                final int pos = i;
                final int cipherLetter = ALPHABET.get(aux.charAt(j));

                plainTextBuilder.append(ALPHABET.entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() == ((cipherLetter + 26 - letterShift[pos]) % 26))
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .get());
            }
            plainTextSplit.add(plainTextBuilder.toString());
        }

        for (int i = 0; i < plainTextSplit.get(0).length(); i++) {
            for (int j = 0; j < key.length(); j++) {
                if (plainTextSplit.get(j).length() > i) {
                    plainText.append(plainTextSplit.get(j).charAt(i));
                }
            }
        }
        return plainText.toString();
    }


    private static ArrayList<String> splitText(final int keySize, final String cipherText) {
        final ArrayList<String> textSplit = new ArrayList<>();
        StringBuilder builder;

        for (int i = 0; i < keySize; i++) {
            builder = new StringBuilder();
            for (int j = i; j < cipherText.length(); j += keySize) {
                builder.append(cipherText.charAt(j));
            }
            textSplit.add(builder.toString());
        }

        return textSplit;
    }
}
