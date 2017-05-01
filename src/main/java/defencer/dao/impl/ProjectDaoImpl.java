package defencer.dao.impl;

import defencer.dao.ProjectDao;
import defencer.model.Project;
import defencer.util.HibernateUtil;
import org.hibernate.Session;

import java.time.*;
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
    public List<Project> getProjectForGivenPeriod(Long defaultPeriod) {
        LocalDate localDate = LocalDate.now().minusDays(defaultPeriod);
        final Session session = getSession();
        session.beginTransaction();
        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Project> projectCriteriaQuery = criteriaBuilder.createQuery(Project.class);
        final Root<Project> root = projectCriteriaQuery.from(Project.class);
        projectCriteriaQuery.select(root)
                .where(criteriaBuilder
                        .between(root.get("dateOfCreation"), localDate, LocalDate.now().plusDays(1)));
        final List<Project> projects = session.createQuery(projectCriteriaQuery).getResultList();
        session.getTransaction().commit();
        session.close();
        return projects;
    }

    @Override
    public void saveId(Long projectId) {

    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Project> getFindProject(Long periodInDays, String projectName) {
        if ("".equals(projectName)) {
            return getProjectForGivenPeriod(periodInDays);
        }
        LocalDate localDate = LocalDate.now().minusDays(periodInDays);
        final Session session = getSession();
        session.beginTransaction();
        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Project> projectCriteriaQuery = criteriaBuilder.createQuery(Project.class);
        final Root<Project> root = projectCriteriaQuery.from(Project.class);
        projectCriteriaQuery.select(root)
                .where(criteriaBuilder
                                .between(root.get("dateOfCreation"), localDate, LocalDate.now().plusDays(1)),
                        criteriaBuilder.equal(root.get("name"), projectName));
        final List<Project> projects = session.createQuery(projectCriteriaQuery).getResultList();
        session.getTransaction().commit();
        session.close();
        return projects;
    }

    /**
     * @return {@link Session} for next steps.
     */
    private Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }
}
