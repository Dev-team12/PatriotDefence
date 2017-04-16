package defencer.dao.impl;

import defencer.dao.InstructorDao;
import defencer.model.Instructor;
import defencer.model.Project;
import defencer.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.LinkedList;
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
    public Instructor findByEmail(String email) {
        final Session session = getSession();
        session.beginTransaction();
        System.out.println(email);
        final Query query = session.createQuery("from Instructor where email = :email");
        query.setParameter("email", email);
        session.getTransaction().commit();
        return null;// todo create something.
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Instructor findByPhone(String phone) {
        final Session session = getSession();
        return null;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Project> findProjectByInstructor(Long id) {
        final Session session = getSession();
        return null;
    }

    @Override
    public List<Instructor> getInstructors() {
        final Session session = getSession();
        final Query query = session.createQuery("from Instructor");
        return query.list();
    }

    private Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }
}
