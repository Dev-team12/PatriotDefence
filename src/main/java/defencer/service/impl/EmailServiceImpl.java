package defencer.service.impl;

import defencer.config.EmailConfig;
import defencer.data.CurrentUser;
import defencer.service.EmailBuilder;
import defencer.service.EmailService;
import defencer.service.impl.email.PdfReportBuilder;
import defencer.util.NotificationUtil;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

/**
 * Implementation of {@link EmailService} interface.
 *
 * @author Igor Gnes on 5/2/17.
 */
public class EmailServiceImpl implements EmailService {

    /**
     * {@inheritDoc}.
     */
    @Override
    public void sendMessage(SimpleMailMessage message) {
        try {
            new EmailConfig().mailSender().send(message);
        } catch (Exception e) {
            // NON
        }
    }

    /**
     * {@inheritDoc}.
     */
    @SneakyThrows
    @Override
    public void sendMessageWithAttachments(FileSystemResource file) {
        final EmailConfig emailConfig = new EmailConfig();
        final MimeMessage mimeMessage = emailConfig.mailSender().createMimeMessage();
        EmailBuilder<CurrentUser> emailBuilder = new PdfReportBuilder();
        val messageHelper = emailBuilder.buildMessageWithAttachment(CurrentUser.getLink(), mimeMessage);
        messageHelper.addAttachment(file.getFilename(), file);
        emailConfig.mailSender().send(mimeMessage);
        NotificationUtil.warningAlert("Success", CurrentUser.getLink().getFirstName() + " please check your email to see your report doc.", NotificationUtil.SHORT);
    }
}
