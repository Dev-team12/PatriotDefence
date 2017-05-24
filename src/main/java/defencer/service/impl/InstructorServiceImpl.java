package defencer.service.impl;

import defencer.dao.factory.DaoFactory;
import defencer.data.CurrentUser;
import defencer.exception.entity.EntityAlreadyExistsException;
import defencer.model.Instructor;
import defencer.model.Project;
import defencer.model.Schedule;
import defencer.service.*;
import defencer.service.cryptography.CryptoInstructor;
import defencer.service.cryptography.CryptoProject;
import defencer.service.factory.ServiceFactory;
import defencer.service.impl.email.ConfirmBuilderImpl;
import defencer.service.impl.email.InviteProjectBuilderImpl;
import defencer.util.telegram.TelegramUtil;
import lombok.val;
import org.apache.commons.lang.RandomStringUtils;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of {@link InstructorService} interface.
 *
 * @author igor on 28.11.16.
 */
public class InstructorServiceImpl extends CrudServiceImpl<Instructor> implements InstructorService {

    private static final int PASSWORD_LENGTH = 12;

    /**
     * {@inheritDoc}.
     */
    @Override
    public Instructor createEntity(Instructor instructor) throws SQLException {
        if (!this.emailAvailable(instructor)) {
            throw new EntityAlreadyExistsException("Supplied email is already taken: " + instructor.getEmail());
        }

        val instructorPassword = RandomStringUtils.randomAlphanumeric(PASSWORD_LENGTH);
        EmailBuilder<Instructor> emailBuilder = new ConfirmBuilderImpl();
        instructor.setPassword(instructorPassword);
        val message = emailBuilder.buildMessage(instructor);
        ServiceFactory.getEmailService().sendMessage(message);
        CryptoService<Instructor> cryptoService = new CryptoInstructor();
        return super.createEntity(cryptoService.encryptEntity(instructor));
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Instructor findByEmail(String email) {
        final Instructor instructor = DaoFactory.getInstructorDao().findByEmail(email);
        CryptoService<Instructor> cryptoService = new CryptoInstructor();
        return cryptoService.decryptEntity(instructor);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Instructor> getInstructors() {
        final List<Instructor> instructors = DaoFactory.getInstructorDao().getInstructors();
        CryptoService<Instructor> cryptoService = new CryptoInstructor();
        return cryptoService.decryptEntityList(instructors);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void configureProject(List<Instructor> instructors, Project project) {
        CurrentUser.refresh(CurrentUser.getLink().getEmail());

        ServiceFactory.getWiseacreService().updateExpected(instructors, project);

        TelegramUtil.getLink().alertAboutProject(instructors, project);

        final Thread email = new Thread(mailSender(instructors, project));
        email.start();

        final Thread sms = new Thread(smsSender(instructors, project));
        sms.start();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Schedule> getMyProject(Long userId) {
        return DaoFactory.getInstructorDao().getMyProject(userId);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void changePassword(Long userId, String password) {
        CryptoService<Instructor> cryptoService = new CryptoInstructor();
        final String encryptedPassword = cryptoService.encryptSimpleText(password);
        DaoFactory.getInstructorDao().changePassword(userId, encryptedPassword);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void deleteInstructor(Long instructorId) {
        DaoFactory.getInstructorDao().deleteInstructor(instructorId);
    }

    /**
     * Send notifications to instructors by email.
     */
    private Runnable mailSender(List<Instructor> instructors, Project project) {
        final StringBuffer stringBuffer = new StringBuffer();
        instructors.forEach(s -> stringBuffer.append(s.getFirstLastName()));
        stringBuffer.append(project.getInstructors()).append(" ").append(project.getExpected());
        project.setInstructors(stringBuffer.toString());
        EmailBuilder<Instructor> emailBuilder = new InviteProjectBuilderImpl();
        final EmailService emailService = ServiceFactory.getEmailService();
        return () -> instructors.forEach(s -> emailService.sendMessage(emailBuilder
                .buildMessageForProject(s, project)));
    }

    /**
     * Send notifications to instructors by sms.
     */
    private Runnable smsSender(List<Instructor> instructors, Project project) {
        final StringBuffer stringBuffer = new StringBuffer();
        instructors.forEach(s -> stringBuffer.append(s.getFirstLastName()));
        stringBuffer.append(project.getInstructors()).append(" ").append(project.getExpected());
        project.setInstructors(stringBuffer.toString());
        final SmsService smsService = ServiceFactory.getSmsService();
        return () -> instructors.forEach(s -> smsService.send(s.getPhone(), s, project));
    }
    /**
     * Checks if supplied email is already in the database.
     *
     * @param instructor to check email for.
     * @return true if email available, false otherwise.
     */
    private boolean emailAvailable(Instructor instructor) {
        val email = instructor.getEmail();
        return this.findByEmail(email) == null;
    }
}
