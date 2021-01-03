package part2;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CipherBody implements Strategy {

    private Cipher cipher;
    private final java.security.Key aesKey;

    /**
     * Constructor. It initializes the key that will be used to cipher and decipher
     */
    public CipherBody() {
        String key = "IWantToPassTAP12"; // 128 bit key
        aesKey =
                new javax.crypto.spec.SecretKeySpec(key.getBytes(), "AES");
        try {
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that ciphers the body of the message
     * @param body -> body of the message
     * @return body of the message ciphered
     */
    @Override
    public String sendMail(String body) {

        byte[] encrypted = new byte[0];

        try {
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            encrypted = cipher.doFinal(body.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(encrypted);
    }

    /**
     * Method that deciphers the body of the message
     * @param body -> body of the message ciphered
     * @return body of the message deciphered
     */
    @Override
    public String getMail(String body) {
        byte[] encrypted = Base64.getDecoder().decode(body.getBytes());
        String decrypted = null;

        try {
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            decrypted = new String(cipher.doFinal(encrypted));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return decrypted;
    }


}
