package defencer.service.cryptography;

import defencer.model.Apprentice;
import defencer.service.CryptoService;
import lombok.SneakyThrows;

import java.security.PublicKey;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;

/**
 * @author Igor Hnes on 22.05.17.
 */
public class CryptoApprentice extends CryptoServiceImpl implements CryptoService<Apprentice> {

    /**
     * {@inheritDoc}.
     */
    @SneakyThrows
    @Override
    public Apprentice encryptEntity(Apprentice apprentice) {
        Cipher cipher = Cipher.getInstance("RSA");
        PublicKey key = generatePublicKey();
        cipher.init(Cipher.ENCRYPT_MODE, key);

        apprentice.setName(new String(Base64.getEncoder()
                .encode(cipher.doFinal(apprentice.getName().getBytes()))));

        apprentice.setPhone(new String(Base64.getEncoder()
                .encode(cipher.doFinal(apprentice.getPhone().getBytes()))));

        apprentice.setEmail(new String(Base64.getEncoder()
                .encode(cipher.doFinal(apprentice.getEmail().getBytes()))));

        apprentice.setOccupation(new String(Base64.getEncoder()
                .encode(cipher.doFinal(apprentice.getOccupation().getBytes()))));

        return apprentice;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Apprentice decryptEntity(Apprentice apprentice) {
        try {
            apprentice.setName(decryption(apprentice.getName()));
            apprentice.setPhone(decryption(apprentice.getPhone()));
            apprentice.setEmail(decryption(apprentice.getEmail()));
            apprentice.setOccupation(decryption(apprentice.getOccupation()));
        } catch (Exception e) {
            return apprentice;
        }
        return apprentice;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Apprentice> decryptEntityList(List<Apprentice> apprentices) {
        for (Apprentice apprentice : apprentices) {
            try {
                apprentice.setName(decryption(apprentice.getName()));
                apprentice.setPhone(decryption(apprentice.getPhone()));
                apprentice.setEmail(decryption(apprentice.getEmail()));
                apprentice.setOccupation(decryption(apprentice.getOccupation()));
            } catch (Exception e) {
                apprentice.setName(apprentice.getName());
                apprentice.setPhone(apprentice.getPhone());
                apprentice.setEmail(apprentice.getEmail());
                apprentice.setOccupation(apprentice.getOccupation());
            }
        }
        return apprentices;
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
