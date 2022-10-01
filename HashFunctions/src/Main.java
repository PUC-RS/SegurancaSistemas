import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Main {
    private static final String PATH = "./src/Resources/";
    private static final Integer BYTE_SIZE = 1024;

    private static Integer sizeOfLastChunk = 0;

    private static byte[] readFile(final String fileName) throws IOException {
        final String fullPath = PATH + fileName;

        return Files.readAllBytes(Paths.get(fullPath));
    }

    private static ArrayList<byte[]> splitBytes(final byte[] bytes) {
        ArrayList<byte[]> bytesChunks = new ArrayList<>();

        for (int i = 0; i < bytes.length; i = i + BYTE_SIZE) {
            byte[] slice = new byte[BYTE_SIZE];
            for (int j = 0; j < BYTE_SIZE; j++) {
                if (j + i < bytes.length)
                    slice[j] = bytes[j + i];
                else {
                    sizeOfLastChunk = j;
                    break;
                }
            }
            bytesChunks.add(slice);
        }
        return bytesChunks;
    }

    private static byte[] getH0(final ArrayList<byte[]> divBytes) throws NoSuchAlgorithmException {
        byte[] lastChunk = divBytes.remove(divBytes.size() - 1);
        byte[] lastBlock = new byte[sizeOfLastChunk];
        System.arraycopy(lastChunk, 0, lastBlock, 0, lastBlock.length);

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(lastBlock);

        byte[] hash = digest.digest();

        for (int i = divBytes.size() - 1; i >= 0; i--) {
            byte[] bloco = divBytes.get(i);
            byte[] toHash = new byte[hash.length + bloco.length];
            System.arraycopy(bloco, 0, toHash, 0, bloco.length);
            System.arraycopy(hash, 0, toHash, bloco.length, hash.length);
            digest.update(toHash);
            hash = digest.digest();
        }
        return hash;
    }

    public static void main(String[] args) {
        try {
            byte[] bytes = readFile("FuncoesResumo - SHA1.mp4");
            ArrayList<byte[]> bytesSlices = splitBytes(bytes);
            final byte[] hashAnterior = getH0(bytesSlices);

            StringBuilder sb = new StringBuilder(hashAnterior.length * 2);
            for (byte b : hashAnterior)
                sb.append(String.format("%02x", b));
            System.out.println("Found H0:");
            System.out.println(sb);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}