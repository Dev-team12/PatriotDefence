package defencer.service.impl;


import defencer.model.Pupil;
import defencer.service.PupilService;

import java.sql.SQLException;

/**
 * Basic implementation of {@link PupilService} interface.
 *
 * @author igor on 06.12.16.
 */
public class PupilServiceImpl extends CrudServiceImpl<Pupil> implements PupilService {

    @Override
    public Pupil createEntity(Pupil entity) throws SQLException {
        return super.createEntity(entity);
    }

    @Override
    public void deleteEntity(Long id) throws SQLException {
        super.deleteEntity(id);
    }

    @Override
    public Pupil updateEntity(Pupil pupil) throws SQLException {
        return super.updateEntity(pupil);
    }
}
