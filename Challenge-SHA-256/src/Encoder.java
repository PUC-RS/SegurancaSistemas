import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encoder {

    private static final String name = "Francisco";

    private static final String bites = "000000000000000000000000000000";

    public static String encode() throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        for (int i = 0; true; i++) {
            final String stringForHash = name.concat(String.valueOf(i));
            System.out.println("Tentative: " + stringForHash);

            byte[] encodedHash = digest.digest(stringForHash.getBytes(StandardCharsets.UTF_8));

            final String binaryHash = toBinary(encodedHash);
            if (bites.equals(binaryHash.substring(0, 30))) {
                return bytesToHex(encodedHash);
            }
        }
    }

    private static String toBinary(final byte[] encodedHash) {
        StringBuilder sb = new StringBuilder(encodedHash.length * Byte.SIZE);
        for (int i = 0; i < Byte.SIZE * encodedHash.length; i++)
            sb.append((encodedHash[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        return sb.toString();
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
