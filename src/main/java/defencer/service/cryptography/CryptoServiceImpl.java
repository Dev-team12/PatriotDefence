package defencer.service.cryptography;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Properties;
import javax.crypto.Cipher;

/**
 * @author Igor Hnes on 22.05.17.
 */
class CryptoServiceImpl {

    private final String path = "src/main/resources/crypto_key.properties";
    private final Properties properties = new Properties();

    @SneakyThrows
    private String getPrivateKey() {
        final FileInputStream file = new FileInputStream(path);
        properties.load(file);
        return properties.getProperty("private.key");
    }

    @SneakyThrows
    private String getPublicKey() {
        final FileInputStream file = new FileInputStream(path);
        properties.load(file);
        return properties.getProperty("public.key");
    }

    /**
     * SneakyThrows because algorithm RSA is correct.
     *
     * @return already generated public key.
     * @throws InvalidKeySpecException if key in properties file incorrect.
     */
    @SneakyThrows
    PublicKey generatePublicKey() throws InvalidKeySpecException {
        byte[] encoded = Base64.getDecoder().decode(getPublicKey());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * SneakyThrows because algorithm RSA is correct.
     *
     * @return already generated private key.
     * @throws InvalidKeySpecException if key in properties file incorrect.
     */
    @SneakyThrows
    PrivateKey generatePrivateKey() throws InvalidKeySpecException {
        byte[] encoded = Base64.getDecoder().decode(getPrivateKey());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * @param encryptedMessage going to be decrypted.
     * @return a decrypted text.
     * @throws Exception if method can't decrypt a message
     *                   and that method who invoked it will return text from database.
     */
    String decryption(String encryptedMessage) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        PrivateKey key = generatePrivateKey();
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] cipherTextBytes = Base64.getDecoder().decode(encryptedMessage.getBytes());
        byte[] decryptedBytes = cipher.doFinal(cipherTextBytes);
        return new String(decryptedBytes);
    }
}
