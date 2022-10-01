import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final String PATH = "./src/Resources/";
    private static final Integer BYTE_SIZE = 1024;

    private static Integer sizeOfLastChunk = 0;

    private static byte[] readFile(final String fileName) throws IOException {
        final String fullPath = PATH + fileName;

        return Files.readAllBytes(Paths.get(fullPath));
    }

    private static ArrayList<byte[]> divideChunks(final byte[] fileInBytes) {
        final ArrayList<byte[]> chunks = new ArrayList<>();

        for (int i = 0; i < fileInBytes.length; i += BYTE_SIZE) {
            byte[] fatia = new byte[BYTE_SIZE];
            for (int j = 0; j < BYTE_SIZE; j++) {
                if (j + i < fileInBytes.length)
                    fatia[j] = fileInBytes[j + i];
                else {
                    sizeOfLastChunk = j;
                    break;
                }
            }
            chunks.add(fatia);
        }

        return chunks;
    }

    private static byte[] findFirstHash(final ArrayList<byte[]> chunks, final MessageDigest digest) {
        byte[] hash = digest.digest();

        for (int i = chunks.size() - 1; i >= 0; i--) {
            byte[] bloco = chunks.get(i);
            byte[] toHash = new byte[hash.length + bloco.length];
            System.arraycopy(bloco, 0, toHash, 0, bloco.length);
            System.arraycopy(hash, 0, toHash, bloco.length, hash.length);
            digest.update(toHash);
            hash = digest.digest();
        }

        return hash;
    }

    public static void main(String[] args) {
        final Scanner in = new Scanner(System.in);
        System.out.println("Witch file do you want?");
        final String fileName = in.nextLine();
        try {
            final byte[] fileInBytes = readFile(fileName);
            final ArrayList<byte[]> chunks = divideChunks(fileInBytes);
            final byte[] lastChunk = chunks.remove(chunks.size() - 1);
            final byte[] copyLastChunk = new byte[sizeOfLastChunk];
            System.arraycopy(lastChunk, 0, copyLastChunk, 0, copyLastChunk.length);

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(lastChunk);

            byte[] hash = findFirstHash(chunks, digest);

            StringBuilder builder = new StringBuilder(hash.length * 2);
            for (byte b : hash)
                builder.append(String.format("%02x", b));
            System.out.println("Found h0:");
            System.out.println(builder);
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }
}