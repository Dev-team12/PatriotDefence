package defencer.service.cryptography;

import defencer.model.Schedule;
import defencer.service.CryptoService;
import lombok.SneakyThrows;

import java.security.PublicKey;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;

/**
 * @author Igor Hnes on 23.05.17.
 */
public class CryptoSchedule extends CryptoServiceImpl implements CryptoService<Schedule> {

    /**
     * {@inheritDoc}.
     */
    @SneakyThrows
    @Override
    public Schedule encryptEntity(Schedule schedule) {
        Cipher cipher = Cipher.getInstance("RSA");
        PublicKey key = generatePublicKey();
        cipher.init(Cipher.ENCRYPT_MODE, key);

        schedule.setInstructorName(new String(Base64.getEncoder()
                .encode(cipher.doFinal(schedule.getInstructorName().getBytes()))));

        schedule.setProjectName(new String(Base64.getEncoder()
                .encode(cipher.doFinal(schedule.getProjectName().getBytes()))));

        schedule.setStatus(new String(Base64.getEncoder()
                .encode(cipher.doFinal(schedule.getStatus().getBytes()))));

        return schedule;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Schedule> decryptEntityList(List<Schedule> schedules) {
        for (Schedule schedule : schedules) {
            try {
                schedule.setInstructorName(decryption(schedule.getInstructorName()));
                schedule.setProjectName(decryption(schedule.getProjectName()));
                schedule.setStatus(decryption(schedule.getStatus()));
            } catch (Exception e) {
                schedule.setInstructorName(schedule.getInstructorName());
                schedule.setProjectName(schedule.getProjectName());
                schedule.setStatus(schedule.getStatus());
            }
        }
        return schedules;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Schedule decryptEntity(Schedule schedule) {
        try {
            schedule.setInstructorName(decryption(schedule.getInstructorName()));
            schedule.setProjectName(decryption(schedule.getProjectName()));
            schedule.setStatus(decryption(schedule.getStatus()));
        } catch (Exception e) {
            return schedule;
        }
        return schedule;
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
