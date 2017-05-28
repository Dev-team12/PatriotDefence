package defencer.service.impl;

import defencer.dao.factory.DaoFactory;
import defencer.dao.impl.WiseacreDaoImpl;
import defencer.model.Instructor;
import defencer.model.Project;
import defencer.model.ProjectTimes;
import defencer.service.CryptoService;
import defencer.service.EmailBuilder;
import defencer.service.EmailService;
import defencer.service.ProjectService;
import defencer.service.cryptography.CryptoInstructor;
import defencer.service.cryptography.CryptoProject;
import defencer.service.factory.ServiceFactory;
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
        final CryptoService<Project> cryptoService = new CryptoProject();
        final List<Project> projects = DaoFactory.getProjectDao().getProjectForGivenPeriod(DEFAULT_PERIOD);
        return cryptoService.decryptEntityList(projects);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Project> getFindProject(Long periodInDays, String projectName) {
        final CryptoService<Project> cryptoService = new CryptoProject();
        final List<Project> projectsList = DaoFactory.getProjectDao().getFindProject(periodInDays, projectName);
        return cryptoService.decryptEntityList(projectsList);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void deleteProject(Long projectId) {
        DaoFactory.getProjectDao().deleteProject(projectId);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Project> getProjectsToCalendar() {
        return DaoFactory.getProjectDao().getProjectsToCalendar();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Project createEntity(Project project) throws SQLException {
        final ProjectTimes projectTimes = new ProjectTimes();
        projectTimes.setDateOfCreation(LocalDate.now());
        projectTimes.setProjectName(project.getName());
        WiseacreDaoImpl wiseacreDao = new WiseacreDaoImpl();
        wiseacreDao.save(projectTimes);
        final CryptoService<Project> cryptoService = new CryptoProject();
        final Project encryptProject = cryptoService.encryptEntity(project);
        final Project alreadyCreatedProject = super.createEntity(encryptProject);
        final List<Instructor> admins = findAdmins();
        final Thread thread = new Thread(mailSenderCreatedProject(admins, project));
        thread.start();

        return alreadyCreatedProject;
    }

    /**
     * @return list of instructors who has role Chief Officer.
     */
    private List<Instructor> findAdmins() {
        CryptoService<Instructor> cryptoService = new CryptoInstructor();
        return cryptoService.decryptEntityList(DaoFactory.getInstructorDao().findAdmins());
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
}
