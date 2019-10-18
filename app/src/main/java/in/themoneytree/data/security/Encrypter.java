package in.themoneytree.data.security;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Encrypter {
    public static String key = "secretkeyforencryption1234567890";

    public boolean changeKey(String key) {
        this.key = key;
        return true;
    }

    public boolean setUpDefaultKey() {
        this.key = "secretkeyforencryption1234567890";
        return true;
    }

    public static String passwordManiputlator(String text) {
        int asciiSum = 0;
        for (int i = 0; i < text.length(); i++) {
            asciiSum = (int) text.charAt(i) + asciiSum;
        }
        String manipulatedPassword = asciiSum + "";
        return manipulatedPassword;
    }

    public static byte[] encryption(String text) {
        String encrypt = "";

        try {
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            return encrypted;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static String decryption(byte[] encrypted) {
        String decrypt = "";
        try {
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            decrypt = new String(cipher.doFinal(encrypted));
        } catch (Exception e) {
            System.out.println(e);
        }
        return decrypt;
    }
}
