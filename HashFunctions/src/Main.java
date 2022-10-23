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

    /**
     * Read the bytes for a given file.
     *
     * @param fileName The file name
     * @return The bytes of the file
     * @throws IOException When the file can not be reached
     */
    private static byte[] readFileBytes(final String fileName) throws IOException {
        final String fullPath = PATH + fileName;

        return Files.readAllBytes(Paths.get(fullPath));
    }

    /**
     * Split the byte array in minor arrays of 1024 bytes.
     *
     * @param bytes The file content
     * @return ArrayList of multiple byte arrays of 1024 length
     */
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

    /**
     * Finds the hash for the first block of 1024 bytes.
     *
     * @param bytesChunks The array of 2014 byte array
     * @return The hash for the first 1024 bytes
     * @throws NoSuchAlgorithmException When no cryptographic algorithm is requested
     *                                  but is not available in the environment
     */
    private static byte[] getH0(final ArrayList<byte[]> bytesChunks) throws NoSuchAlgorithmException {
        byte[] lastChunk = bytesChunks.remove(bytesChunks.size() - 1);
        byte[] lastBlock = new byte[sizeOfLastChunk];
        System.arraycopy(lastChunk, 0, lastBlock, 0, lastBlock.length);

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(lastBlock);

        byte[] hash = digest.digest();

        for (int i = bytesChunks.size() - 1; i >= 0; i--) {
            byte[] chunk = bytesChunks.get(i);
            byte[] copyChunk = new byte[hash.length + chunk.length];
            System.arraycopy(chunk, 0, copyChunk, 0, chunk.length);
            System.arraycopy(hash, 0, copyChunk, chunk.length, hash.length);
            digest.update(copyChunk);
            hash = digest.digest();
        }
        return hash;
    }

    public static void main(String[] args) {
        /*
            According to the project description, the expected result for FuncoesResumo - SHA1.mp4 is
            302256b74111bcba1c04282a1e31da7e547d4a7098cdaec8330d48bd87569516
         */

        try {
            //byte[] bytes = readFileBytes("FuncoesResumo - Hash Functions.mp4");
            byte[] bytes = readFileBytes("FuncoesResumo - SHA1.mp4");
            ArrayList<byte[]> bytesSlices = splitBytes(bytes);
            final byte[] hash = getH0(bytesSlices);

            StringBuilder sb = new StringBuilder(hash.length * 2);
            for (byte b : hash)
                sb.append(String.format("%02x", b));
            System.out.println("Found H0:");
            System.out.println(sb);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
