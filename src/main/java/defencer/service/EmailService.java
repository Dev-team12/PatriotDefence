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
     * Send message to instructor with information about their project.
     */
    SimpleMailMessage simpleMailMessage(Instructor instructor, Project project);

    /**
     * Send message to instructor with PDF or EXCEL documents.
     */
    MimeMailMessage mimeMailMessage(Instructor instructor);
}
