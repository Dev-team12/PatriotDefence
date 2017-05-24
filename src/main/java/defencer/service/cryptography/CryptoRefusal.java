package defencer.service.cryptography;

import defencer.model.Refusal;
import defencer.service.CryptoService;
import lombok.SneakyThrows;

import java.security.PublicKey;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;

/**
 * @author Igor Hnes on 23.05.17.
 */
public class CryptoRefusal extends CryptoServiceImpl implements CryptoService<Refusal> {

    /**
     * {@inheritDoc}.
     */
    @SneakyThrows
    @Override
    public Refusal encryptEntity(Refusal refusal) {
        Cipher cipher = Cipher.getInstance("RSA");
        PublicKey key = generatePublicKey();
        cipher.init(Cipher.ENCRYPT_MODE, key);

        refusal.setInstructorNames(new String(Base64.getEncoder()
                .encode(cipher.doFinal(refusal.getInstructorNames().getBytes()))));

        return refusal;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Refusal decryptEntity(Refusal refusal) {
        try {
            refusal.setInstructorNames(decryption(refusal.getInstructorNames()));
        } catch (Exception e) {
            return refusal;
        }
        return refusal;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Refusal> decryptEntityList(List<Refusal> refusals) {
        for (Refusal refusal : refusals) {
            try {
                refusal.setInstructorNames(decryption(refusal.getInstructorNames()));
            } catch (Exception e) {
                refusal.setInstructorNames(refusal.getInstructorNames());
            }
        }
        return refusals;
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
