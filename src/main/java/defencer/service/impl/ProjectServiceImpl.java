package defencer.service.impl;

import defencer.model.Project;
import defencer.service.BuildService;

import java.sql.SQLException;
import java.util.List;

/**
 * @author igor on 22.11.16.
 */
public class ProjectServiceImpl implements BuildService<Project> {

    @Override
    public int createEntity(Project entity) throws SQLException {
        return 0;
    }

    @Override
    public List<Project> getTwelfth() throws SQLException {
        return null;
    }

    @Override
    public List<Project> getByValue(String value, String like) throws SQLException {
        return null;
    }

    @Override
    public void deleteEntity(String like) throws SQLException {

    }

    @Override
    public void updateEntity(String like, String name, String phone, String email, String description, String occupation, String projectName) throws SQLException {

    }
}
