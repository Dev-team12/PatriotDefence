package defencer.service.impl.email;

import defencer.model.AbstractEntity;
import defencer.model.Project;
import defencer.service.EmailBuilder;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.Assert;

import javax.mail.internet.MimeMessage;

/**
 * @param <T> is entity type.
 * @author Igor Gnes on 5/3/17.
 */
public abstract class EmailBuilderImpl<T extends AbstractEntity> implements EmailBuilder<T> {

    /**
     * {@inheritDoc}.
     */
    @Override
    public SimpleMailMessage buildMessage(T entity) {
        Assert.notNull(entity, "entity must not be null");

        val message = this.buildProperties(entity);
        message.setSubject(this.getMessageSubject());
        message.setText(this.getMessageBody(entity, null));
        return message;
    }

    @SneakyThrows
    @Override
    public MimeMessageHelper buildMessageWithAttachment(T entity, MimeMessage mimeMessage) {
        final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(this.getMessageRecipient(entity));
        String sender = "projectpatriotdefence";
        messageHelper.setFrom(sender);
        messageHelper.setSubject(this.getMessageSubject());
        messageHelper.setText(this.getMessageBody(entity, null));
        return messageHelper;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public SimpleMailMessage buildMessageForProject(T entity, Project project) {
        Assert.notNull(entity, "entity must not be null");
        Assert.notNull(project, "project must not be null");

        val message = this.buildProperties(entity);
        message.setSubject(this.getMessageSubject());
        message.setText(this.getMessageBody(entity, project));
        return message;
    }

    /**
     * @return ready message.
     */
    private SimpleMailMessage buildProperties(T entity) {
        val simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(this.getMessageRecipient(entity));
        String sender = "projectpatriotdefence";
        simpleMailMessage.setFrom(sender);
        return simpleMailMessage;
    }

    abstract String getMessageRecipient(T entity);

    abstract String getMessageSubject();

    abstract String getMessageBody(T entity, Project project);
}
