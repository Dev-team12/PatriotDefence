package defencer.service.impl;

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
    public Project createEntity(Project entity) throws SQLException {
        return super.createEntity(entity);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void deleteEntity(Long id) throws SQLException {
        super.deleteEntity(id);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Project updateEntity(Project project) throws SQLException {
        return super.updateEntity(project);
    }

    @Override
    public List<Project> findByPeriod() {
        return null;
    }
}
