package defencer.dao.impl;

import defencer.dao.ApprenticeDao;
import defencer.model.Apprentice;
import defencer.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
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

    @Override
    public List<Apprentice> getApprentice() {
        final Session session = getSession();
        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Apprentice> criteriaQuery = criteriaBuilder.createQuery(Apprentice.class);
        final Root<Apprentice> root = criteriaQuery.from(Apprentice.class);
        criteriaQuery.select(root);
        return session.createQuery(criteriaQuery).getResultList();
    }

    private Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

}
