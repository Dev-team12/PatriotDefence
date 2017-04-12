package defencer.dao.impl;

import defencer.dao.PupilDao;
import defencer.model.Pupil;


/**
 * Implementations of {@link PupilDao} interface.
 *
 * @author Igor Gnes on 4/9/17.
 */
public class PupilDaoImpl extends CrudDaoImpl<Pupil> implements PupilDao {

    /**
     * {@inheritDoc}.
     */
    @Override
    public Pupil findByProject(Long id) {
        return null;
    }
}
