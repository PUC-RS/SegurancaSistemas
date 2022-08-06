import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CoincidenceIndex {
    static final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    static final String PATH = "./CoincidenceIndex/resources/";

    public static void main(String[] args) {
        for (int i = 1; i <= 7; i++) {
            final String fileName = "T" + i + ".txt";
            final String text = readSource(fileName);

            final float coincidence = calculateCoincidence(text);
            System.out.printf("Coincidence Index for file %s: %.4f %n", fileName, coincidence);
        }

        final String fileName = "TextoClaro.txt";
        final String text = readSource(fileName);

        final float coincidence = calculateCoincidence(text);
        System.out.printf("Coincidence Index for file %s: %.4f %n", fileName, coincidence);
    }

    private static float calculateCoincidence(final String text) {
        final long textLength = text.length();
        float sum = 0;

        for (final char letter: alphabet){
            final long letterFrequency = text.chars().filter(c -> c == letter).count();
            sum += (letterFrequency * (letterFrequency - 1));
        }
        return sum / (textLength * (textLength - 1));
    }

    private static String readSource(String fileName) {
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
            System.out.println("File was not found");
            e.printStackTrace();
        }

        return textContent.toString();
    }
}
