package defencer.dao.impl;

import defencer.dao.InstructorDao;
import defencer.model.Instructor;
import defencer.model.Project;

import java.util.List;


/**
 * Implementation of {@link InstructorDao} interface.
 *
 * @author Igor Gnes on 3/30/17.
 */
public class InstructorDaoImpl extends CrudDaoImpl<Instructor> implements InstructorDao {

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

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(Long id) {

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
    public Instructor update(Instructor instructor) {
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

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Project> findProjectByInstructor(Long id) {
        return null;
    }
}
