package defencer.service.impl;

import defencer.config.EmailConfig;
import defencer.model.Instructor;
import defencer.model.Project;
import defencer.service.EmailService;
import lombok.val;
import org.springframework.mail.MailSendException;
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
        final EmailConfig emailConfig = new EmailConfig();
        val message = buildProperties(instructor);
        final String messageBuilder = messageBuilder(instructor.getFirstLastName(), project);
        message.setText(messageBuilder);
        try {
            emailConfig.mailSender().send(message);
        } catch (MailSendException e) {
            // NON
//            NotificationUtil.errornAlert("Wrong Email Address", instructor.getEmail(), NotificationUtil.SHORT);
        }
        return message;
    }

    /**
     * Build text message.
     * @return text for message.
     */
    private String messageBuilder(String name, Project project) {

        return "Dear "
                + name
                + " you were invite in course from Patriot Defence."
                + "\n"
                + "Project "
                + project.getNameId()
                + "\n"
                + "Date Start "
                + project.getDateStart()
                + " Date Finish "
                + project.getDateFinish()
                + "\n"
                + "Place "
                + project.getPlace()
                + "\n"
                + "Description "
                + project.getDescription()
                + "\n"
                + "Full list of instructors "
                + "\n"
                + project.getInstructors()
                + "\n"
                + "Author of project "
                + project.getAuthor()
                + "\n"
                + "Have a nice day -)"
                + "\n"
                + "Your Patriot Defence!!!";
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
