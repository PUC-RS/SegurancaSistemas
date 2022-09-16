import Decrypter.VereneDecrypt;
import Utils.KeyUtils;
import Utils.SourceReader;
import Utils.SourceWriter;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static String fileName = "ptPlainText.txt";
    static String cipherText = "";

    public static void main(String[] args) {
        try {
            cipherText = SourceReader.readSource(fileName);
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        final KeyUtils keyFinder = new KeyUtils(cipherText);
        keyFinder.findKeys();

        ArrayList<String> possibleKeysEn = keyFinder.getKeysEn();
        ArrayList<String> possibleKeysPt = keyFinder.getKeysPt();

        final Scanner in = new Scanner(System.in);

        if (!possibleKeysPt.isEmpty()) {
            System.out.println("Trying to decipher the text for portuguese");
            System.out.println("There is " + possibleKeysPt.size() + " keys to test");

            for (int i = 0; i < possibleKeysPt.size(); i++) {
                System.out.println("---------------------------------------------------------------------------------");
                System.out.println("Testing key: " + possibleKeysPt.get(i));
                System.out.println("Deciphering text");

                final String plainText = VereneDecrypt.decipherText(cipherText, possibleKeysPt.get(i));

                System.out.println("Text decipher successfully: \n" + plainText.substring(0, 50) + "\n");
                System.out.println("Should try next key ? (Y/N)");

                final String op = in.nextLine();

                if (op.equalsIgnoreCase("N")) {
                    final String outputFileName = fileName + "OutputPt.txt";
                    SourceWriter.writeFile(plainText, outputFileName);
                    System.exit(0);
                }
            }
            System.out.println("The possible keys for portuguese has finished");
        } else {
            System.out.println("Any keys for portuguese were found");
        }

        System.out.println("-----------------------------------------------------------------------------------------");

        if (!possibleKeysEn.isEmpty()) {
            System.out.println("Trying to decipher the text for english");
            System.out.println("There is " + possibleKeysEn.size() + " keys to test");

            for (int i = 0; i < possibleKeysEn.size(); i++) {
                System.out.println("---------------------------------------------------------------------------------");
                System.out.println("Testing key: " + possibleKeysEn.get(i));
                System.out.println("Deciphering text");

                final String plainText = VereneDecrypt.decipherText(cipherText, possibleKeysEn.get(i));

                System.out.println("Text decipher successfully: \n" + plainText.substring(0, 50) + "\n");
                System.out.println("Should try next key ? (Y/N)");

                final String op = in.nextLine();

                if (op.equalsIgnoreCase("N")) {
                    final String outputFileName = fileName + "OutputEn.txt";
                    SourceWriter.writeFile(plainText, outputFileName);
                    System.exit(0);
                }
            }
            System.out.println("The possible keys for english has finished");
        } else {
            System.out.println("Any keys for english were found");
        }
    }
}
