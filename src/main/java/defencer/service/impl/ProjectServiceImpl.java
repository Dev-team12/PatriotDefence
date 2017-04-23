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

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Project> findByPeriod(Long months) {
        return null;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Project> getProjectsForLastMonths() {
        return DaoFactory.getProjectDao().getProjectForGivenPeriod();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Project createEntity(Project project) throws SQLException {
        return super.createEntity(project);
    }
}
