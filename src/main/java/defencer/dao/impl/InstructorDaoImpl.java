package defencer.dao.impl;

import defencer.dao.InstructorDao;
import defencer.model.Instructor;
import defencer.model.Project;
import defencer.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
        Instructor instructor;
        final Query emailQuery = session.createQuery("from Instructor where email = :email").setParameter("email", email);
        try {
            instructor = (Instructor) emailQuery.getSingleResult();
        } catch (NoResultException e) {
            session.getTransaction().commit();
            session.close();
            return null;
        }
        session.getTransaction().commit();
        session.close();
        return instructor;
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
    public Project findProjectByInstructor(Long id) {
        final Session session = getSession();
        session.beginTransaction();

        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Project> projectCriteriaQuery = criteriaBuilder.createQuery(Project.class);
        final Root<Project> root = projectCriteriaQuery.from(Project.class);
        projectCriteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
        final Project singleResult;
        try {
            singleResult = session.createQuery(projectCriteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            session.close();
            return null;
        }

        session.getTransaction().commit();
        session.close();
        return singleResult;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Instructor> getInstructors() {
        final Session session = getSession();
        session.beginTransaction();
        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Instructor> criteriaQuery = criteriaBuilder.createQuery(Instructor.class);
        final Root<Instructor> root = criteriaQuery.from(Instructor.class);
        criteriaQuery.multiselect(root.get("id"), root.get("firstName"), root.get("lastName"), root.get("qualification"),
                root.get("role"), root.get("phone"), root.get("status"), root.get("email"));
        final List<Instructor> instructorList = session.createQuery(criteriaQuery).getResultList();
        session.getTransaction().commit();
        session.close();
        return instructorList;
    }

    /**
     * @return {@link Session} for next steps.
     */
    private Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }
}
