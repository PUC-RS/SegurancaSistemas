package Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SourceReader {
    static final String PATH = "./VigenereDecrypt/src/resources/";

    /**
     * Read the file content to a String variable
     * @param fileName The encrypted text file
     * @return The content of the file
     * @throws FileNotFoundException The file was not found at ./src/resources/
     */
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
