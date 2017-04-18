package defencer.dao.impl;

import defencer.dao.WiseacreDao;
import defencer.model.AvailableProject;
import defencer.model.Car;
import defencer.model.Instructor;
import defencer.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Implementation of {@link WiseacreDao} interface.
 *
 * @author Igor Gnes on 4/17/17.
 */
public class WiseacreDaoImpl implements WiseacreDao {

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Car> getFreeCar() {
        final Session session = getCurrentSession();
        session.beginTransaction();
        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Car> carCriteriaQuery = criteriaBuilder.createQuery(Car.class);
        final Root<Car> root = carCriteriaQuery.from(Car.class);
        final List<Car> carList = session.createQuery(carCriteriaQuery
                .where(criteriaBuilder.equal(root.get("status"), "FREE"))).getResultList();
        session.getTransaction().commit();
        session.close();
        return carList;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<AvailableProject> getAvailableProject() {
        final Session session = getCurrentSession();
        session.beginTransaction();
        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<AvailableProject> availableProjectCriteriaQuery = criteriaBuilder.createQuery(AvailableProject.class);
        final Root<AvailableProject> root = availableProjectCriteriaQuery.from(AvailableProject.class);
        availableProjectCriteriaQuery.select(root);
        final List<AvailableProject> availableProjects = session.createQuery(availableProjectCriteriaQuery).getResultList();
        session.getTransaction().commit();
        session.close();
        return availableProjects;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Instructor> getFreeInstructors() {
        final Session session = getCurrentSession();
        session.beginTransaction();
        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Instructor> availableProjectCriteriaQuery = criteriaBuilder.createQuery(Instructor.class);
        final Root<Instructor> root = availableProjectCriteriaQuery.from(Instructor.class);
        final List<Instructor> instructorList = session.createQuery(availableProjectCriteriaQuery
                .where(criteriaBuilder.equal(root.get("status"), "FREE"))).getResultList();
        session.getTransaction().commit();
        session.close();
        return instructorList;
    }

    /**
     * @return {@link Session} for next steps.
     */
    private Session getCurrentSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }
}
