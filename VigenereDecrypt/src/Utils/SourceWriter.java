package Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SourceWriter {
    private static final String PATH = "./VigenereDecrypt/src/output/";

    /**
     * Create a file with decrypted text
     * @param content The plain text String
     * @param fileName The file name to be created
     */
    public static void writeFile(final String content, final String fileName) {
        final String pathWithFileName = PATH + fileName;
        new File(pathWithFileName);

        try {
            final FileWriter writer = new FileWriter(pathWithFileName);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
