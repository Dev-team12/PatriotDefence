package defencer.service;

import defencer.model.Project;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

/**
 * @author Igor Gnes on 5/3/17.
 *
 * @param <T> is entity type.
 */
public interface EmailBuilder<T> {

    /**
     * Constructs {@link SimpleMailMessage} for provided entity.
     *
     * @param entity an entity to construct email for.
     * @return complete {@link SimpleMailMessage} email message.
     */
    SimpleMailMessage buildMessage(T entity);

    /**
     * @param entity an to construct email for.
     * @param project is project on that invited entity.
     * @return complete {@link SimpleMailMessage} email message.
     */
    SimpleMailMessage buildMessageForProject(T entity, Project project);

    /**
     * @param entity an to construct email for.
     * @return complete {@link MimeMessageHelper} email message.
     */
    MimeMessageHelper buildMessageWithAttachment(T entity, MimeMessage mimeMessage);
}
