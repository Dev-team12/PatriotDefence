package defencer.config;

import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author Igor Gnes on 5/2/17.
 */
public class EmailConfig {

    private static final Integer PORT = 587;

    /**
     * @return configured {@link JavaMailSenderImpl}.
     */
    public JavaMailSenderImpl mailSender() {

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(PORT);
        javaMailSender.setUsername("projectpatriotdefence");
        javaMailSender.setPassword("SmertVoroham");
        javaMailSender.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
        javaMailSender.getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true");
        return javaMailSender;
    }
}
