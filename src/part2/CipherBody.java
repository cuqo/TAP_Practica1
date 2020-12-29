package part2;

import part1.Message;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

public class CipherBody implements Strategy{

    private Cipher cipher;
    private java.security.Key aesKey;

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
