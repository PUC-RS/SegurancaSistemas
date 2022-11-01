import java.math.BigInteger;

/**
 * a = "9876543211234567890098765432112345678900"
 * <p>
 * A = "93ca3b3a56011fbac2208e6ce116a32f2b11e0ba833052415e88799386a346aa288f9d52ba8b1f241717e1436592003b7162ab897ca
 * 09fb1d660cb063cef7b55f0912c87436f9085a8172dd26489f4b0c4ef122f36f547b860be829da13895326184ac5b074fc12cb117e1ae319
 * 2914e4da9da616e50e9000c3f9c6448f837f8"
 * <p>
 * B = "31B69140823C5FE6659505F38B8A2A0E35E799411B22A24A1E1510BD7991E05760CD556A9686A5F879DFF9BCBD39E8CE4D114741C48
 * 030D912E2D3126150F0918A436D68C0A6C8F29A9AC41AF5507D3934133545723865B0531FC1A7CF3693E2EABB2C482FDE878E6E6947B2C9F
 * C3A31E71BEFDC8CA474C203B19F9A739749FB"
 * <p>
 * Mensagem do professor = "A78CEAFBCD078D88BC493A18E78B7086184F2F994557466BDF16EE46D6BE4AB60AF2906BC1FB0531F7F7B61
 * 63080F9E7F0EF94B0FA746D5775181F2EA462374DF86DD16D4D66922166ED9D1A64C135F4B56B41D19A66247A4A69C182963D6387E1DC0BE
 * 6DEC886C2AE1CDEC67757735B3117A018C20C7B63816909F26B87683B"
 * <p>
 * Mensagem invertida = "14c4df42472264048bb0f3fec28146c3e9442972bfea7028184fe16c6be06348975785e8f2a08b0f185e589627
 * 8a0955752c39396d74c29e5fdb1bf8176023d70e8edfa3f4b5cdcc736f9c47f275b1586f097f7c4864d1341acc034dfa7320dbf1d1bec390
 * 98b8294b8e47019f6a92af9852d1de5795b4bfb0e85fbe963f4585"
 *
 * Classe responsavel pelas chamadas de métodos e mostragem em tela dos resultados obtidos
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Generated value for 'a' (dec): " + DiffieHellman.getLittleA().toString());
        System.out.println("Generated value for 'a' (hex): " + DiffieHellman.getLittleA().toString(16));

        final BigInteger A = DiffieHellman.calculateA();

        System.out.println("Generated value for 'A' (hex): " + A.toString(16));

        final byte[] key = DiffieHellman.getKey();
        System.out.println("Found the key: " + byteToString(key));

        System.out.println("Decrypting message");
        final String plainText = Message.Decrypt(key, "A78CEAFBCD078D88BC493A18E78B7086184F2F994557466BDF16E" +
                "E46D6BE4AB60AF2906BC1FB0531F7F7B6163080F9E7F0EF94B0FA746D5775181F2EA462374DF86DD16D4D66922166ED9D1A" +
                "64C135F4B56B41D19A66247A4A69C182963D6387E1DC0BE6DEC886C2AE1CDEC67757735B3117A018C20C7B63816909F26B8" +
                "7683B");

        System.out.println("Message found: " + plainText);

        final String invertedPlainText = new StringBuilder(plainText).reverse().toString();
        System.out.println("Reversed message: " + invertedPlainText);

        final byte[] encryptedInvertedMessageBytes = Message.Encrypt(key, invertedPlainText);
        final String encryptedInvertedMessage = byteToString(encryptedInvertedMessageBytes);

        System.out.println("Message encrypted: " + encryptedInvertedMessage);
    }


    private static String byteToString(final byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}