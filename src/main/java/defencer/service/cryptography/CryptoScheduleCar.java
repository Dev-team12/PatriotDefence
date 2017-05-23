package defencer.service.cryptography;

import defencer.model.ScheduleCar;
import defencer.service.CryptoService;
import lombok.SneakyThrows;

import java.security.PublicKey;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;

/**
 * @author Igor Hnes on 23.05.17.
 */
public class CryptoScheduleCar extends CryptoServiceImpl implements CryptoService<ScheduleCar> {

    /**
     * {@inheritDoc}.
     */
    @SneakyThrows
    @Override
    public ScheduleCar encryptEntity(ScheduleCar scheduleCar) {
        Cipher cipher = Cipher.getInstance("RSA");
        PublicKey key = generatePublicKey();
        cipher.init(Cipher.ENCRYPT_MODE, key);

        scheduleCar.setCarName(new String(Base64.getEncoder()
                .encode(cipher.doFinal(scheduleCar.getCarName().getBytes()))));
        return scheduleCar;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public ScheduleCar decryptEntity(ScheduleCar scheduleCar) {
        try {
            scheduleCar.setCarName(decryption(scheduleCar.getCarName()));
        } catch (Exception e) {
            return scheduleCar;
        }
        return scheduleCar;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<ScheduleCar> decryptEntityList(List<ScheduleCar> scheduleCars) {
        for (ScheduleCar scheduleCar : scheduleCars) {
            try {
                scheduleCar.setCarName(decryption(scheduleCar.getCarName()));
            } catch (Exception e) {
                scheduleCar.setCarName(scheduleCar.getCarName());
            }
        }
        return scheduleCars;
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
