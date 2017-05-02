package defencer.service.impl;

import defencer.model.Instructor;
import defencer.model.Project;
import defencer.service.EmailService;
import lombok.val;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMailMessage;

/**
 * Implementation of {@link EmailService} interface.
 *
 * @author Igor Gnes on 5/2/17.
 */
public class EmailServiceImpl implements EmailService {

    @Override
    public SimpleMailMessage simpleMailMessage(Instructor instructor, Project project) {
        val message = buildProperties(instructor);
        messageBuilder(instructor.getFirstLastName(), project);
        return null;
    }

    /**
     * Build text message.
     * @return text for message.
     */
    private String messageBuilder(String name, Project project) {

        return "Dear "
                + name
                + "you were invite in course from Patriot Defence."
                + "Project "
                + project.getName()
                + " "
                + "Date Start "
                + project.getDateStart()
                + "Date Finish "
                + project.getDateFinish();

    }

    @Override
    public MimeMailMessage mimeMailMessage(Instructor instructor) {
        return null;
    }

    /**
     * Build properties for message.
     */
    private SimpleMailMessage buildProperties(Instructor instructor) {

        val message = new SimpleMailMessage();
        message.setTo(instructor.getEmail());
        message.setSubject("Project Patriot Defence");
        return message;
    }
}
