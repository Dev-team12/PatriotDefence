package defencer.service.cryptography;

import defencer.model.AvailableProject;
import defencer.service.CryptoService;
import lombok.SneakyThrows;

import java.security.PublicKey;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;

/**
 * @author Igor Hnes on 23.05.17.
 */
public class CryptoProjectTypes extends CryptoServiceImpl implements CryptoService<AvailableProject> {

    /**
     * {@inheritDoc}.
     */
    @SneakyThrows
    @Override
    public AvailableProject encryptEntity(AvailableProject availableProject) {
        Cipher cipher = Cipher.getInstance("RSA");
        PublicKey key = generatePublicKey();
        cipher.init(Cipher.ENCRYPT_MODE, key);

        availableProject.setProjectName(new String(Base64.getEncoder()
                .encode(cipher.doFinal(availableProject.getProjectName().getBytes()))));

        return availableProject;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public AvailableProject decryptEntity(AvailableProject availableProject) {
        try {
            availableProject.setProjectName(decryption(availableProject.getProjectName()));
        } catch (Exception e) {
            return availableProject;
        }
        return availableProject;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<AvailableProject> decryptEntityList(List<AvailableProject> availableProjects) {
        for (AvailableProject availableProject : availableProjects) {
            try {
                availableProject.setProjectName(decryption(availableProject.getProjectName()));
            } catch (Exception e) {
                availableProject.setProjectName(availableProject.getProjectName());
            }
        }
        return availableProjects;
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
