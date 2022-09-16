import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Main {
    private static final String key = "140b41b22a29beb4061bda66b6747e14";

    private static final String encryptedTextWithIv =
            "4e657874205468757273646179206f6e65206f66207468652062657374207465616d7320696e2074686520776f726c642077696c6c2066616365206120626967206368616c6c656e676520696e20746865204c696265727461646f72657320646120416d6572696361204368616d70696f6e736869702e";

    private static final String plainText = "4e657874205468757273646179206f6e65206f66207468652062657374207465616d7320696e2074686520776f726c642077696c6c2066616365206120626967206368616c6c656e676520696e20746865204c696265727461646f72657320646120416d6572696361204368616d70696f6e736869702e";
    private static final byte[] initVector = DatatypeConverter.parseHexBinary(encryptedTextWithIv.substring(0, 32));

    public static void main(String[] args) {
    }

    private static void decryptCBC() {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec skeySpec = new SecretKeySpec(DatatypeConverter.parseHexBinary(key), "AES");

            final String encryptedText = encryptedTextWithIv.substring(32);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(HexBin.decode(encryptedText));

            System.out.println(new String(original));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void encryptCBC() {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec skeySpec = new SecretKeySpec(DatatypeConverter.parseHexBinary(key), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(plainText.getBytes());
            System.out.println(DatatypeConverter.printHexBinary(encrypted));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}