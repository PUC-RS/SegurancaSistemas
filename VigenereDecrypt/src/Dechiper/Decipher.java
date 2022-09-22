package Dechiper;

import static Dechiper.KeyFinder.searchKey;
import static Dechiper.KeyFinder.searchKeySize;

public class Decipher {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public static String decryptFile(final String text) {
        int keySize = searchKeySize(text);

        String key = searchKey(keySize, text);

        System.out.println("\n\nKey = " + key + "     Size = " + keySize);
        final StringBuilder builder = new StringBuilder();

        int j = 0;
        for (int i = 0; i < text.length(); i++) {
            if (j == key.length())
                j = 0;
            int condition = text.charAt(i) - key.charAt(j);
            if (condition >= 0)
                builder.append(ALPHABET.charAt(condition));
            else
                builder.append(ALPHABET.charAt(26 + condition));
            j++;
        }
        return builder.toString();
    }
}
