package defencer.dao.impl;

import defencer.dao.ProjectDao;
import defencer.model.Project;
import defencer.util.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDate;
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

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Project> getProjectForLastMonths() {
        int months = 1; // todo replace from view
        LocalDate localDate = LocalDate.now().minusMonths(months);
        final Session session = getSession();
        session.beginTransaction();
        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Project> projectCriteriaQuery = criteriaBuilder.createQuery(Project.class);
        final Root<Project> root = projectCriteriaQuery.from(Project.class);
        final List<Project> projects = session.createQuery(projectCriteriaQuery
                .where(criteriaBuilder
                        .between(root.get("dateOfCreation"), localDate, LocalDate.now().plusDays(months)))).getResultList();

        session.getTransaction().commit();
        session.close();
        return projects;
    }

    @Override
    public void saveId(Long projectId) {

    }

    /**
     * @return {@link Session} for next steps.
     */
    private Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }
}
