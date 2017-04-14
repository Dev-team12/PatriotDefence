package defencer.service.impl;


import defencer.dao.factory.DaoFactory;
import defencer.model.Apprentice;
import defencer.service.ApprenticeService;

/**
 * Basic implementation of {@link ApprenticeService} interface.
 *
 * @author igor on 06.12.16.
 */
public class ApprenticeServiceImpl extends CrudServiceImpl<Apprentice> implements ApprenticeService {

    /**
     * {@inheritDoc}.
     */
    @Override
    public Apprentice findByProject(Long id) {
        return DaoFactory.getPupilDao().findByProject(id);
    }
}
