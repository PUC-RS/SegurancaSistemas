package Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SourceReader {
    static final String PATH = "./VigenereDecrypt/src/resources/";

    public static String readSource(String fileName) {
        fileName = PATH + fileName;

        final StringBuilder textContent = new StringBuilder();
        try {
            File file = new File(fileName);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                textContent.append(reader.nextLine());
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File was not found on path: " + PATH);
            e.printStackTrace();
        }

        return textContent.toString();
    }
}
