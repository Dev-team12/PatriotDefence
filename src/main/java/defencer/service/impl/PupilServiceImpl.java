package defencer.service.impl;


import defencer.dao.factory.DaoFactory;
import defencer.model.Pupil;
import defencer.service.PupilService;

/**
 * Basic implementation of {@link PupilService} interface.
 *
 * @author igor on 06.12.16.
 */
public class PupilServiceImpl extends CrudServiceImpl<Pupil> implements PupilService {

    /**
     * {@inheritDoc}.
     */
    @Override
    public Pupil findByProject(Long id) {
        return DaoFactory.getPupilDao().findByProject(id);
    }
}
