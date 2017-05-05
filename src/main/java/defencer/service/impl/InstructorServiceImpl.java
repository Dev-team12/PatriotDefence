package defencer.service.impl;

import defencer.dao.factory.DaoFactory;
import defencer.data.CurrentUser;
import defencer.exception.entity.EntityAlreadyExistsException;
import defencer.model.Instructor;
import defencer.model.Project;
import defencer.service.EmailBuilder;
import defencer.service.EmailService;
import defencer.service.InstructorService;
import defencer.service.factory.ServiceFactory;
import defencer.service.impl.email.ConfirmBuilderImpl;
import defencer.service.impl.email.InviteProjectBuilderImpl;
import lombok.val;
import org.apache.commons.lang.RandomStringUtils;

import java.sql.SQLException;
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
        EmailBuilder<Instructor> emailBuilder = new ConfirmBuilderImpl();
        val message = emailBuilder.buildMessage(instructor);
        ServiceFactory.getEmailService().sendMessage(message);
        val instructorPassword = RandomStringUtils.randomAlphanumeric(PASSWORD_LENGTH);
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        final String encode = encoder.encode(instructorPassword);
        instructor.setPassword(instructorPassword);
        return super.createEntity(instructor);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Project findProjectByInstructor(Long id) {
        return DaoFactory.getInstructorDao().findProjectByInstructor(id);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Instructor findByEmail(String email) {
        return DaoFactory.getInstructorDao().findByEmail(email);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Instructor> getInstructors() {
        return DaoFactory.getInstructorDao().getInstructors();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void configureProject(List<Instructor> instructors, Project project) {
        Runnable runnable = () -> instructors.forEach(s -> {
            s.setProjectId(project.getId());
            s.setStatus("EXPECTED");
            try {
                ServiceFactory.getInstructorService().updateEntity(s);
                ServiceFactory.getProjectService().updateEntity(project);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        final Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CurrentUser.refresh(CurrentUser.getLink().getEmail());

        final Thread email = new Thread(mailSender(instructors, project));
        email.start();
    }

    /**
     * Send notifications to instructors.
     */
    private Runnable mailSender(List<Instructor> instructors, Project project) {
        EmailBuilder<Instructor> emailBuilder = new InviteProjectBuilderImpl();
        final EmailService emailService = ServiceFactory.getEmailService();
        return () -> instructors.forEach(s -> emailService.sendMessage(emailBuilder
                .buildMessageForProject(s, project)));
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
