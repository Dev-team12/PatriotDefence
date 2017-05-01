package defencer.service.impl;

import defencer.dao.factory.DaoFactory;
import defencer.model.Project;
import defencer.service.ProjectService;

import java.sql.SQLException;
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
    public Project createEntity(Project project) throws SQLException {
        return super.createEntity(project);
    }
}
