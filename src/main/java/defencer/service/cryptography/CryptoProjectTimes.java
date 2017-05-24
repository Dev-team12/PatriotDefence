package defencer.service.cryptography;

import defencer.model.ProjectTimes;
import defencer.service.CryptoService;
import lombok.SneakyThrows;

import java.security.PublicKey;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;

/**
 * @author Igor Hnes on 23.05.17.
 */
public class CryptoProjectTimes extends CryptoServiceImpl implements CryptoService<ProjectTimes> {

    /**
     * {@inheritDoc}.
     */
    @SneakyThrows
    @Override
    public ProjectTimes encryptEntity(ProjectTimes projectTimes) {
        Cipher cipher = Cipher.getInstance("RSA");
        PublicKey key = generatePublicKey();
        cipher.init(Cipher.ENCRYPT_MODE, key);

        projectTimes.setProjectName(new String(Base64.getEncoder()
                .encode(cipher.doFinal(projectTimes.getProjectName().getBytes()))));

        return projectTimes;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public ProjectTimes decryptEntity(ProjectTimes projectTimes) {
        try {
            projectTimes.setProjectName(decryption(projectTimes.getProjectName()));
        } catch (Exception e) {
            return projectTimes;
        }
        return projectTimes;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<ProjectTimes> decryptEntityList(List<ProjectTimes> projectTimes) {
        for (ProjectTimes projectTime : projectTimes) {
            try {
                projectTime.setProjectName(decryption(projectTime.getProjectName()));
            } catch (Exception e) {
                projectTime.setProjectName(projectTime.getProjectName());
            }
        }
        return projectTimes;
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
