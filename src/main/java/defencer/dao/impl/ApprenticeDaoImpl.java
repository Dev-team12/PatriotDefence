package defencer.dao.impl;

import defencer.dao.ApprenticeDao;
import defencer.model.Apprentice;
import defencer.util.HibernateUtil;
import lombok.val;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
    public List<Apprentice> findByPeriod(Long period, String projectName) {
        if ("".equals(projectName)) {
            return getApprentice();
        }
        final LocalDate localDate = LocalDate.now().minusDays(period);
        final Session session = getSession();
        session.beginTransaction();
        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Apprentice> criteriaQuery = criteriaBuilder.createQuery(Apprentice.class);
        final Root<Apprentice> root = criteriaQuery.from(Apprentice.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.between(root.get("dateOfAdded"), localDate, LocalDate.now().plusDays(1)),
                        criteriaBuilder.equal(root.get("projectName"), projectName));
        final List<Apprentice> apprenticeList = session.createQuery(criteriaQuery).getResultList();
        session.getTransaction().commit();
        session.close();
        return apprenticeList;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Apprentice> getApprentice() {
        int months = 1;
        LocalDate localDate = LocalDate.now().minusMonths(months);
        final Session session = getSession();
        session.beginTransaction();
        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Apprentice> criteriaQuery = criteriaBuilder.createQuery(Apprentice.class);
        final Root<Apprentice> root = criteriaQuery.from(Apprentice.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder
                        .between(root.get("dateOfAdded"), localDate, LocalDate.now().plusDays(months)));
        final List<Apprentice> apprenticeList = session.createQuery(criteriaQuery).getResultList();
        session.getTransaction().commit();
        session.close();
        return apprenticeList;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Apprentice findByEmail(String email) {
        final Session session = getSession();
        session.beginTransaction();
        Apprentice apprentice;
        final Query emailQuery = session.createQuery("from Apprentice where email = :email").setParameter("email", email);
        try {
            apprentice = (Apprentice) emailQuery.getSingleResult();
        } catch (NoResultException e) {
            session.getTransaction().commit();
            session.close();
            return null;
        }
        session.getTransaction().commit();
        session.close();
        return apprentice;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void deleteApprenticeById(Long apprenticeId) {
        final Session session = getSession();
        session.beginTransaction();

        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        val criteriaDelete = criteriaBuilder.createCriteriaDelete(Apprentice.class);
        final Root<Apprentice> root = criteriaDelete.from(Apprentice.class);

        criteriaDelete.where(criteriaBuilder.equal(root.get("id"), apprenticeId));
        session.createQuery(criteriaDelete).executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    /**
     * @return {@link Session} for next steps.
     */
    private Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }
}
