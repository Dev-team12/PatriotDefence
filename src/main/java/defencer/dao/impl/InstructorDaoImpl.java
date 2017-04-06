package defencer.dao.impl;

import defencer.dao.InstuctorDao;
import defencer.model.Instructor;

import java.util.List;

/**
 * @author Igor Gnes on 3/30/17.
 */
public class InstructorDaoImpl extends GrudDaoImpl<Instructor> implements InstuctorDao {


    /**
     * {@inheritDoc}.
     */
    @Override
    public Instructor save(Instructor instructor) {
        return super.save(instructor);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Instructor findOne(Long id) {

        return null;
    }

    private Long test(Long id) {

        return null;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(Instructor entity) {

    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean exists(Long aLong) {
        return false;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Instructor update(Long aLong) {
        return null;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Instructor> getEntityNames() {
        return null;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Instructor findByEmail(String email) {
        return null;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Instructor findByPhone(String phone) {
        return null;
    }
}
