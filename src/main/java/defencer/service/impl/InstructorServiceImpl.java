package defencer.service.impl;

import defencer.model.Instructor;
import defencer.service.BuildService;

import java.sql.SQLException;
import java.util.List;

/**
 * @author igor on 28.11.16.
 */
public class InstructorServiceImpl implements BuildService<Instructor> {

    @Override
    public int createEntity(Instructor entity) throws SQLException {
        return 0;
    }

    @Override
    public List<Instructor> getTwelfth() throws SQLException {
        return null;
    }

    @Override
    public List<Instructor> getByValue(String value, String like) throws SQLException {
        return null;
    }

    @Override
    public void deleteEntity(String like) throws SQLException {

    }

    @Override
    public void updateEntity(String like, String name, String phone, String email, String description, String occupation, String projectName) throws SQLException {

    }
}
