package defencer.dao.impl;

import defencer.dao.ApprenticeDao;
import defencer.model.Apprentice;
import defencer.util.HibernateUtil;
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
    public Apprentice findByProject(Long id) {
        return null;
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
        final List<Apprentice> apprenticeList = session.createQuery(criteriaQuery
                .where(criteriaBuilder
                        .between(root.get("dateOfAdded"), localDate, LocalDate.now().plusDays(months)))).getResultList();
        session.getTransaction().commit();
        session.close();
        return apprenticeList;
    }

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
     * @return {@link Session} for next steps.
     */
    private Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

}
