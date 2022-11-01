import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe responsavel por:
 * Calculo do 'a', um numero aleatorio de 40 caracteres que será utilizado para calculo do 'A' dado pela formula
 * `A = g ^ a mod p`;
 * <p>
 * Calculo de 'V' utilizando a formula `V = B ^ a mod p`;
 * <p>
 * Calculo de 'S' a partir do já calculado 'V' aplicado SHA-256;
 * <p>
 * Calculo da chave (16 primeiros bytes de S);
 */
public class DiffieHellman {
    private static final BigInteger a = new BigInteger("9876543211234567890098765432112345678900", 16);

    private static final BigInteger p = new BigInteger("B10B8F96A080E01DDE92DE5EAE5D54EC52C99FBCFB06A3C69A6A9" +
            "DCA52D23B616073E28675A23D189838EF1E2EE652C013ECB4AEA906112324975C3CD49B83BFACCBDD7D90C4BD7098488E9C2" +
            "19A73724EFFD6FAE5644738FAA31A4FF55BCCC0A151AF5F0DC8B4BD45BF37DF365C1A65E68CFDA76D4DA708DF1FB2BC2E4A4" +
            "371", 16);

    private static final BigInteger g = new BigInteger("A4D1CBD5C3FD34126765A442EFB99905F8104DD258AC507FD6406" +
            "CFF14266D31266FEA1E5C41564B777E690F5504F213160217B4B01B886A5E91547F9E2749F4D7FBD7D3B9A92EE1909D0D226" +
            "3F80A76A6A24C087A091F531DBF0A0169B6A28AD662A4D18E73AFA32D779D5918D08BC8858F4DCEF97C2A24855E6EEB22B3B" +
            "2E5", 16);

    private DiffieHellman() {
    }

    public static BigInteger calculateA() {
        return g.modPow(a, p);
    }

    public static byte[] calculateS() {
        final byte[] S;
        try {
            final BigInteger B = new BigInteger("31B69140823C5FE6659505F38B8A2A0E35E799411B22A24A1E1510BD7991E" +
                    "05760CD556A9686A5F879DFF9BCBD39E8CE4D114741C48030D912E2D3126150F0918A436D68C0A6C8F29A9AC41AF5" +
                    "507D3934133545723865B0531FC1A7CF3693E2EABB2C482FDE878E6E6947B2C9FC3A31E71BEFDC8CA474C203B19F9" +
                    "A739749FB", 16);

            final BigInteger V = B.modPow(a, p);

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(V.toByteArray());
            S = digest.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return S;
    }

    public static byte[] getKey() {
        final byte[] s = new byte[16];
        System.arraycopy(calculateS(), 0, s, 0, s.length);
        return s;
    }

    public static BigInteger getLittleA() {
        return a;
    }
}