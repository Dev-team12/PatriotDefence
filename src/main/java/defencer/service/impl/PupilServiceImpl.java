package defencer.service.impl;


import defencer.dao.factory.DaoFactory;
import defencer.model.Apprentice;
import defencer.service.PupilService;

/**
 * Basic implementation of {@link PupilService} interface.
 *
 * @author igor on 06.12.16.
 */
public class PupilServiceImpl extends CrudServiceImpl<Apprentice> implements PupilService {

    /**
     * {@inheritDoc}.
     */
    @Override
    public Apprentice findByProject(Long id) {
        return DaoFactory.getPupilDao().findByProject(id);
    }
}
