import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * Classe responsavel por:
 * <p>
 * Decifrar uma mensagem, com chave conhecida. A decifragem ocorre atraves de uma engenharia reversa, removendo o IV
 * vindo com a mensagem e utilizando-se de `AES/CBC/PKCS5Padding` como algoritmo de decifragem.
 * <p>
 * Cifra uma mensagem, utilizando a mesma chave conhecida, gerando um novo IV, concatenando ele com a mensagem já
 * cifrada
 */
public class Message {
    public static String Decrypt(final byte[] key, final String message) {
        try {
            byte[] messageHex = new byte[message.length() / 2];

            for (int i = 0; i < messageHex.length; i++) {
                int aux = i * 2;
                int val = Integer.parseInt(message.substring(aux, aux + 2), 16);
                messageHex[i] = (byte) val;
            }

            byte[] iv = new byte[16];
            System.arraycopy(messageHex, 0, iv, 0, iv.length);
            IvParameterSpec ivParameter = new IvParameterSpec(iv);

            byte[] messageBytes = new byte[messageHex.length - 16];
            System.arraycopy(messageHex, 16, messageBytes, 0, messageHex.length - 16);

            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameter);

            byte[] decryptedText = cipher.doFinal(messageBytes);

            return new String(decryptedText);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return "Unable to decrypt";
    }

    public static byte[] Encrypt(final byte[] key, final String message) {
        try {
            final byte[] messageBytes = message.getBytes();

            final byte[] randomIv = new byte[16];
            final SecureRandom ran = new SecureRandom();
            ran.nextBytes(randomIv);

            final IvParameterSpec iv = new IvParameterSpec(randomIv);

            final SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");

            final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);

            byte[] encryptedMessage = cipher.doFinal(messageBytes);

            byte[] encryptedMessageWithIv = new byte[randomIv.length + encryptedMessage.length];

            System.arraycopy(randomIv, 0, encryptedMessageWithIv, 0, randomIv.length);
            System.arraycopy(encryptedMessage, 0, encryptedMessageWithIv, randomIv.length,
                    encryptedMessage.length);

            return encryptedMessageWithIv;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
