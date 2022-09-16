package Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SourceReader {
    static final String PATH = "./VigenereDecrypt/src/resources/";

    public static String readSource(String fileName) throws FileNotFoundException {
        fileName = PATH + fileName;

        final StringBuilder textContent = new StringBuilder();

        File file = new File(fileName);
        Scanner reader = new Scanner(file);
        while (reader.hasNextLine()) {
            textContent.append(reader.nextLine());
        }
        reader.close();

        return textContent.toString();
    }
}
