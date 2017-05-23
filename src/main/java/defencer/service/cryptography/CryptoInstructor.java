package defencer.service.cryptography;

import defencer.model.Instructor;
import defencer.service.CryptoService;
import lombok.SneakyThrows;

import java.security.PublicKey;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;

/**
 * @author Igor Hnes on 22.05.17.
 */
public class CryptoInstructor extends CryptoServiceImpl implements CryptoService<Instructor> {

    /**
     * {@inheritDoc}.
     */
    @SneakyThrows
    @Override
    public Instructor encryptEntity(Instructor instructor) {
        Cipher cipher = Cipher.getInstance("RSA");
        PublicKey key = generatePublicKey();
        cipher.init(Cipher.ENCRYPT_MODE, key);

        instructor.setFirstName(new String(Base64.getEncoder()
                .encode(cipher.doFinal(instructor.getFirstName().getBytes()))));

        instructor.setLastName(new String(Base64.getEncoder()
                .encode(cipher.doFinal(instructor.getLastName().getBytes()))));

        instructor.setQualification(new String(Base64.getEncoder()
                .encode(cipher.doFinal(instructor.getQualification().getBytes()))));

        instructor.setPhone(new String(Base64.getEncoder()
                .encode(cipher.doFinal(instructor.getPhone().getBytes()))));

        instructor.setPassword(new String(Base64.getEncoder()
                .encode(cipher.doFinal(instructor.getPassword().getBytes()))));

        instructor.setEmail(new String(Base64.getEncoder()
                .encode(cipher.doFinal(instructor.getEmail().getBytes()))));

        instructor.setVideoPath(new String(Base64.getEncoder()
                .encode(cipher.doFinal(instructor.getVideoPath().getBytes()))));

        return instructor;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Instructor decryptEntity(Instructor instructor) {
        try {
            instructor.setFirstName(decryption(instructor.getFirstName()));
            instructor.setLastName(decryption(instructor.getLastName()));
            instructor.setQualification(decryption(instructor.getQualification()));
            instructor.setPhone(decryption(instructor.getPhone()));
            instructor.setPassword(decryption(instructor.getPassword()));
            instructor.setEmail(decryption(instructor.getEmail()));
            instructor.setVideoPath(decryption(instructor.getVideoPath()));
        } catch (Exception e) {
            return instructor;
        }
        return instructor;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Instructor> decryptEntityList(List<Instructor> instructors) {
        for (Instructor instructor : instructors) {
            try {
                instructor.setFirstName(decryption(instructor.getFirstName()));
                instructor.setLastName(decryption(instructor.getLastName()));
                instructor.setQualification(decryption(instructor.getQualification()));
                instructor.setPhone(decryption(instructor.getPhone()));
                instructor.setPassword(decryption(instructor.getPassword()));
                instructor.setEmail(decryption(instructor.getEmail()));
                instructor.setVideoPath(decryption(instructor.getVideoPath()));
            } catch (Exception e) {
                instructor.setFirstName(instructor.getFirstName());
                instructor.setLastName(instructor.getLastName());
                instructor.setQualification(instructor.getQualification());
                instructor.setPhone(instructor.getPhone());
                instructor.setPassword(instructor.getPassword());
                instructor.setEmail(instructor.getEmail());
                instructor.setVideoPath(instructor.getVideoPath());
            }
        }
        return instructors;
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
