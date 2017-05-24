package defencer.service.cryptography;

import defencer.model.Project;
import defencer.service.CryptoService;
import lombok.SneakyThrows;

import java.security.PublicKey;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;

/**
 * @author Igor Hnes on 22.05.17.
 */
public class CryptoProject extends CryptoServiceImpl implements CryptoService<Project> {

    /**
     * {@inheritDoc}.
     */
    @SneakyThrows
    @Override
    public Project encryptEntity(Project project) {
        Cipher cipher = Cipher.getInstance("RSA");
        PublicKey key = generatePublicKey();
        cipher.init(Cipher.ENCRYPT_MODE, key);

        if (project.getPlace() != null) {
            project.setPlace(new String(Base64.getEncoder()
                    .encode(cipher.doFinal(project.getPlace().getBytes()))));
        }
        if (project.getAuthor() != null) {
            project.setAuthor(new String(Base64.getEncoder()
                    .encode(cipher.doFinal(project.getAuthor().getBytes()))));
        }
        if (project.getDescription() != null) {
            project.setDescription(new String(Base64.getEncoder()
                    .encode(cipher.doFinal(project.getDescription().getBytes()))));
        }
        if (project.getInstructors() != null) {
            project.setInstructors(new String(Base64.getEncoder()
                    .encode(cipher.doFinal(project.getInstructors().getBytes()))));
        }
        if (project.getCars() != null) {
            project.setCars(new String(Base64.getEncoder()
                    .encode(cipher.doFinal(project.getCars().getBytes()))));
        }
        if (project.getRefusal() != null) {
            project.setRefusal(new String(Base64.getEncoder()
                    .encode(cipher.doFinal(project.getRefusal().getBytes()))));
        }
        if (project.getExpected() != null) {
            project.setExpected(new String(Base64.getEncoder()
                    .encode(cipher.doFinal(project.getExpected().getBytes()))));
        }
        return project;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Project decryptEntity(Project project) {
        try {
            project.setPlace(decryption(project.getPlace()));
            project.setAuthor(decryption(project.getAuthor()));
            project.setDescription(decryption(project.getDescription()));

            if (project.getInstructors() != null) {
                project.setInstructors(decryption(project.getInstructors()));
            }
            if (project.getCars() != null) {
                project.setCars(decryption(project.getCars()));
            }
            if (project.getRefusal() != null) {
                project.setRefusal(decryption(project.getRefusal()));
            }
            if (project.getExpected() != null) {
                project.setExpected(decryption(project.getExpected()));
            }
        } catch (Exception e) {
            return project;
        }
        return project;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Project> decryptEntityList(List<Project> projects) {
        for (Project project : projects) {
            try {
                project.setPlace(decryption(project.getPlace()));
                project.setAuthor(decryption(project.getAuthor()));
                project.setDescription(decryption(project.getDescription()));
                if (project.getInstructors() != null) {
                    project.setInstructors(decryption(project.getInstructors()));
                }
                if (project.getCars() != null) {
                    project.setCars(decryption(project.getCars()));
                }
                if (project.getRefusal() != null) {
                    project.setRefusal(decryption(project.getRefusal()));
                }
                if (project.getExpected() != null) {
                    project.setExpected(decryption(project.getExpected()));
                }
            } catch (Exception e) {
                project.setPlace(project.getPlace());
                project.setAuthor(project.getAuthor());
                project.setDescription(project.getDescription());
                if (project.getInstructors() != null) {
                    project.setInstructors(project.getInstructors());
                }
                if (project.getCars() != null) {
                    project.setCars(project.getCars());
                }
                if (project.getRefusal() != null) {
                    project.setRefusal(project.getRefusal());
                }
                if (project.getExpected() != null) {
                    project.setExpected(project.getExpected());
                }
            }
        }
        return projects;
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
