import Utils.SourceWriter;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static Dechiper.Decipher.decryptFile;
import static Utils.SourceReader.readSource;

public class Main {

    private static final String PLAIN_TEXT_PREFIX = "decrypted_";

    public static void main(String[] args) throws FileNotFoundException {
        final Scanner in = new Scanner(System.in);
        System.out.println("What file do you want to decrypt ?");
        final String fileName = in.nextLine();
        final String text = readSource(fileName);
        System.out.println(text.substring(0, 50));
        final String plainText = decryptFile(text);
        System.out.println(plainText.substring(0, 50));
        final String plainTextFileName = PLAIN_TEXT_PREFIX + fileName;
        SourceWriter.writeFile(plainText, plainTextFileName);
    }
}
