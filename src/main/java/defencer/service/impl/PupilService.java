package defencer.service.impl;

import defencer.model.Pupil;
import defencer.service.BuildService;

import java.sql.SQLException;
import java.util.List;

/**
 * @author igor on 06.12.16.
 */
public class PupilService implements BuildService<Pupil> {

    @Override
    public int createEntity(Pupil entity) throws SQLException {
        return 0;
    }

    @Override
    public List<Pupil> getTwelfth() throws SQLException {
        return null;
    }

    @Override
    public List<Pupil> getByValue(String value, String like) throws SQLException {
        return null;
    }

    @Override
    public void deleteEntity(String like) throws SQLException {

    }

    @Override
    public void updateEntity(String like, String name, String phone, String email, String description, String occupation, String projectName) throws SQLException {

    }
}
