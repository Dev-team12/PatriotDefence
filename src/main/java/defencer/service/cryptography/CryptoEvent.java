package defencer.service.cryptography;

import defencer.model.Event;
import defencer.service.CryptoService;
import lombok.SneakyThrows;

import java.security.PublicKey;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;

/**
 * @author Igor Hnes on 23.05.17.
 */
public class CryptoEvent extends CryptoServiceImpl implements CryptoService<Event> {

    /**
     * {@inheritDoc}.
     */
    @SneakyThrows
    @Override
    public Event encryptEntity(Event event) {
        Cipher cipher = Cipher.getInstance("RSA");
        PublicKey key = generatePublicKey();
        cipher.init(Cipher.ENCRYPT_MODE, key);

        event.setName(new String(Base64.getEncoder()
                .encode(cipher.doFinal(event.getName().getBytes()))));
        return event;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Event decryptEntity(Event event) {
        try {
            event.setName(decryption(event.getName()));
        } catch (Exception e) {
            return event;
        }
        return event;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Event> decryptEntityList(List<Event> events) {
        for (Event event : events) {
            try {
                event.setName(decryption(event.getName()));
            } catch (Exception e) {
                event.setName(event.getName());
            }
        }
        return events;
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
