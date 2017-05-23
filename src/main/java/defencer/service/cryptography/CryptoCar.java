package defencer.service.cryptography;

import defencer.model.Car;
import defencer.service.CryptoService;
import lombok.SneakyThrows;

import java.security.PublicKey;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;

/**
 * @author Igor Hnes on 23.05.17.
 */
public class CryptoCar extends CryptoServiceImpl implements CryptoService<Car> {

    /**
     * {@inheritDoc}.
     */
    @SneakyThrows
    @Override
    public Car encryptEntity(Car car) {
        Cipher cipher = Cipher.getInstance("RSA");
        PublicKey key = generatePublicKey();
        cipher.init(Cipher.ENCRYPT_MODE, key);

        car.setCarName(new String(Base64.getEncoder()
                .encode(cipher.doFinal(car.getCarName().getBytes()))));
        return car;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Car decryptEntity(Car car) {
        try {
            car.setCarName(decryption(car.getCarName()));
        } catch (Exception e) {
            return car;
        }
        return car;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Car> decryptEntityList(List<Car> cars) {
        for (Car car : cars) {
            try {
                car.setCarName(decryption(car.getCarName()));
            } catch (Exception e) {
                car.setCarName(car.getCarName());
            }
        }
        return cars;
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
