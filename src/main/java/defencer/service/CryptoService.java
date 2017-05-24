package defencer.service;

import defencer.model.AbstractEntity;

import java.util.List;

/**
 * @param <T> is entity class.
 *
 * @author Igor Hnes on 22.05.17.
 */
public interface CryptoService<T extends AbstractEntity> {

    /**
     * Encrypted given entity.
     */
    T encryptEntity(T entity);

    /**
     * Decrypted given entity.
     */
    T decryptEntity(T entity);

    /**
     * Decrypted list of entity.
     */
    List<T> decryptEntityList(List<T> entity);

    /**
     * Encrypted simple text only.
     */
    String encryptSimpleText(String simpleText);
}
