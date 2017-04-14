package defencer.dao.impl;

import defencer.dao.ApprenticeDao;
import defencer.model.Apprentice;


/**
 * Implementations of {@link ApprenticeDao} interface.
 *
 * @author Igor Gnes on 4/9/17.
 */
public class ApprenticeDaoImpl extends CrudDaoImpl<Apprentice> implements ApprenticeDao {

    /**
     * {@inheritDoc}.
     */
    @Override
    public Apprentice findByProject(Long id) {
        return null;
    }
}
