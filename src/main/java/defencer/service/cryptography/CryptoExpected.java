package defencer.service.cryptography;

import defencer.model.Expected;
import defencer.service.CryptoService;
import lombok.SneakyThrows;

import java.security.PublicKey;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;

/**
 * @author Igor Hnes on 23.05.17.
 */
public class CryptoExpected extends CryptoServiceImpl implements CryptoService<Expected> {

    /**
     * {@inheritDoc}.
     */
    @SneakyThrows
    @Override
    public Expected encryptEntity(Expected expected) {
        Cipher cipher = Cipher.getInstance("RSA");
        PublicKey key = generatePublicKey();
        cipher.init(Cipher.ENCRYPT_MODE, key);

        expected.setInstructorNames(new String(Base64.getEncoder()
                .encode(cipher.doFinal(expected.getInstructorNames().getBytes()))));

        expected.setProjectName(new String(Base64.getEncoder()
                .encode(cipher.doFinal(expected.getProjectName().getBytes()))));

        expected.setStatus(new String(Base64.getEncoder()
                .encode(cipher.doFinal(expected.getStatus().getBytes()))));

        return expected;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Expected decryptEntity(Expected expected) {
        try {
            expected.setInstructorNames(decryption(expected.getInstructorNames()));
            expected.setProjectName(decryption(expected.getProjectName()));
            expected.setStatus(decryption(expected.getStatus()));
        } catch (Exception e) {
            return expected;
        }
        return expected;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Expected> decryptEntityList(List<Expected> expecteds) {
        for (Expected expected : expecteds) {
            try {
                expected.setInstructorNames(decryption(expected.getInstructorNames()));
                expected.setProjectName(decryption(expected.getProjectName()));
                expected.setStatus(decryption(expected.getStatus()));
            } catch (Exception e) {
                expected.setInstructorNames(expected.getInstructorNames());
                expected.setProjectName(expected.getProjectName());
                expected.setStatus(expected.getStatus());
            }
        }
        return expecteds;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public String encryptSimpleText(String simpleText) {
        try {
            return decryption(simpleText);
        } catch (Exception e) {
            return simpleText;
        }
    }
}
