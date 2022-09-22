import java.io.FileNotFoundException;

import static Dechiper.Decipher.decryptFile;
import static Utils.SourceReader.readSource;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        final String fileName = "pt.txt";
        final String text = readSource(fileName);
        final String plainText = decryptFile(text);

        System.out.println(plainText.substring(0, 50));
    }
}
