package defencer.config;

import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author Igor Gnes on 5/2/17.
 */
public class EmailConfig {

    private static final Integer PORT = 587;

    /**
     * @return configured {@link JavaMailSenderImpl}.
     */
    @SneakyThrows
    public JavaMailSenderImpl mailSender() {
        final Properties properties = new Properties();
        final String path = "src/main/resources/crypto_key.properties";
        final FileInputStream file = new FileInputStream(path);
        properties.load(file);

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(properties.getProperty("host"));
        javaMailSender.setPort(PORT);
        javaMailSender.setUsername(properties.getProperty("username"));
        javaMailSender.setPassword(properties.getProperty("password"));
        javaMailSender.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
        javaMailSender.getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true");
        return javaMailSender;
    }
}
