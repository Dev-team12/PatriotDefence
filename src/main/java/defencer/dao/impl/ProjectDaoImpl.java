package defencer.dao.impl;

import defencer.dao.ProjectDao;
import defencer.model.Project;
import defencer.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Implementations of {@link ProjectDao} interface.
 *
 * @author Igor Gnes on 4/10/17.
 */
public class ProjectDaoImpl extends CrudDaoImpl<Project> implements ProjectDao {

    @Override
    public List<Project> getProjectForLastMonths() {
        final Session session = getSession();
        session.beginTransaction();
//        final Query projectQuery = session
//                .createQuery("from Project where dataOfCreation between :lastMonths and :currentDate");
//        projectQuery.setParameter("lastMonths", LocalDateTime.now().minusMonths(1));
//        projectQuery.setParameter("currentDate", LocalDateTime.now());
//        session.getTransaction().commit();

        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);
        final Root<Project> root = criteriaQuery.from(Project.class);
        criteriaQuery.select(root);
        return session.createQuery(criteriaQuery).getResultList();
    }

    private Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }
}
