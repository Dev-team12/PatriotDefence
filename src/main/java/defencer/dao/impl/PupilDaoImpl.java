package defencer.dao.impl;

import defencer.dao.PupilDao;
import defencer.model.Pupil;

import java.util.List;

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
    public Pupil save(Pupil entity) {
        return super.save(entity);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Pupil findOne(Long id) {
        return super.findOne(id);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(Long id) {
        super.delete(id);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean exists(Long id) {
        return super.exists(id);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Pupil update(Pupil entity) {
        return super.update(entity);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Pupil> getEntityNames() {
        return super.getEntityNames();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Pupil findByProject(Long id) {
        return null;
    }
}
