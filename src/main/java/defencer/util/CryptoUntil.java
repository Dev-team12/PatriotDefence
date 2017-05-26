package defencer.util;

import java.io.*;
import java.security.*;
import java.util.Base64;

/**
 * @author Igor Hnes on 22.05.17.
 */
public class CryptoUntil {

    private final int keyLength = 1024;

    /**
     * Generate a private key RSA.
     */
    private String generatePrivateKey() throws NoSuchAlgorithmException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keyLength);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    /**
     * Generate a public key RSA.
     */
    private String generatePublicKey() throws NoSuchAlgorithmException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keyLength);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    /**
     * Prints new RSA keys.
     */
    private void getKeys() throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keyLength);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        String encodedPrivKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        String encodedPubKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());

        System.out.println(encodedPrivKey);
        System.out.println(encodedPubKey);
    }
}