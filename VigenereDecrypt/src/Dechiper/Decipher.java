package Dechiper;

import static Dechiper.KeyFinder.*;

public class Decipher {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    /**
     * Found the plain text for a given cipher text
     * @param text The cipher text
     * @return The plain text
     */
    public static String decryptFile(final String text) {
        for (int k = 0; k < 2; k++){
            setLanguage(k);
            int keySize = searchKeySize(text);

            String key = searchKey(keySize, text);
            if (keySize == 0){
                continue;
            }
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
        return "";
    }
}
