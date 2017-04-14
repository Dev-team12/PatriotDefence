package defencer.dao.impl;

import defencer.dao.PupilDao;
import defencer.model.Apprentice;


/**
 * Implementations of {@link PupilDao} interface.
 *
 * @author Igor Gnes on 4/9/17.
 */
public class PupilDaoImpl extends CrudDaoImpl<Apprentice> implements PupilDao {

    /**
     * {@inheritDoc}.
     */
    @Override
    public Apprentice findByProject(Long id) {
        return null;
    }
}
