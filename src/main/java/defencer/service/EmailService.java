package defencer.service;

import defencer.model.Instructor;
import defencer.model.Project;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMailMessage;

/**
 * @author Igor Gnes on 5/2/17.
 */
public interface EmailService {

    /**
     * @param instructor is going to take a message.
     * @param project    is information for text in email message.
     */
    SimpleMailMessage simpleMailMessage(Instructor instructor, Project project);

    /**
     * @param instructor is going to take a message.
     */
    MimeMailMessage mimeMailMessage(Instructor instructor);
}
