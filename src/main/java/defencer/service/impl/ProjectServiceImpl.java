package defencer.service.impl;

import defencer.dao.factory.DaoFactory;
import defencer.dao.impl.WiseacreDaoImpl;
import defencer.model.Instructor;
import defencer.model.Project;
import defencer.model.ProjectTimes;
import defencer.model.Schedule;
import defencer.service.EmailBuilder;
import defencer.service.EmailService;
import defencer.service.ProjectService;
import defencer.service.factory.ServiceFactory;
import defencer.service.impl.email.AdminReportClosedProjectBuilder;
import defencer.service.impl.email.AdminReportCreatedProjectBuilder;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Basic implementation of {@link ProjectService} interface.
 *
 * @author igor on 22.11.16.
 */
public class ProjectServiceImpl extends CrudServiceImpl<Project> implements ProjectService {

    private static final Long DEFAULT_PERIOD = 30L;

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Project> findByPeriod() {
        return DaoFactory.getProjectDao().getProjectForGivenPeriod(DEFAULT_PERIOD);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Project> getFindProject(Long periodInDays, String projectName) {
        return DaoFactory.getProjectDao().getFindProject(periodInDays, projectName);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void closeProject(Project project) {
        DaoFactory.getProjectDao().closeProject(project.getId());
        try {
            project.setCars("");
            project.setInstructors("");
            ServiceFactory.getProjectService().updateEntity(project);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final List<Instructor> admins = findAdmins();
        final Thread thread = new Thread(mailSenderClosedProject(admins, project));
        thread.start();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Project createEntity(Project project) throws SQLException {
        final Project alreadyCreatedProject = super.createEntity(project);
        final ProjectTimes projectTimes = new ProjectTimes();
        projectTimes.setDateOfCreation(LocalDate.now());
        projectTimes.setProjectName(alreadyCreatedProject.getName());
        WiseacreDaoImpl wiseacreDao = new WiseacreDaoImpl();
        wiseacreDao.save(projectTimes);
        final List<Instructor> admins = findAdmins();
        final Thread thread = new Thread(mailSenderCreatedProject(admins, project));
        thread.start();
        return alreadyCreatedProject;
    }

    /**
     * @return list of instructors who has role Chief Officer.
     */
    private List<Instructor> findAdmins() {
        return DaoFactory.getInstructorDao().findAdmins();
    }

    /**
     * Send report about new project to admin.
     */
    private Runnable mailSenderCreatedProject(List<Instructor> instructors, Project project) {
        EmailBuilder<Instructor> emailBuilder = new AdminReportCreatedProjectBuilder();
        final EmailService emailService = ServiceFactory.getEmailService();
        return () -> instructors.forEach(s -> emailService.sendMessage(emailBuilder
                .buildMessageForProject(s, project)));
    }

    /**
     * Send report about closed project to admin.
     */
    private Runnable mailSenderClosedProject(List<Instructor> instructors, Project project) {
        EmailBuilder<Instructor> emailBuilder = new AdminReportClosedProjectBuilder();
        final EmailService emailService = ServiceFactory.getEmailService();
        return () -> instructors.forEach(s -> emailService.sendMessage(emailBuilder
                .buildMessageForProject(s, project)));
    }
}
