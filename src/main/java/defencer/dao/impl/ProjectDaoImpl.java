package defencer.dao.impl;

import defencer.dao.ProjectDao;
import defencer.model.*;
import defencer.util.HibernateUtil;
import org.hibernate.Session;

import java.time.*;
import java.util.Comparator;
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
        configureProject(projects, criteriaBuilder, session);
        session.getTransaction().commit();
        session.close();
        projects.sort(Comparator.comparing(Project::getId));
        return projects;
    }

    /**
     * Update instructors in project.
     */
    private void configureProject(List<Project> projects, CriteriaBuilder criteriaBuilder, Session session) {
        final CriteriaQuery<Schedule> scheduleCriteriaQuery = criteriaBuilder.createQuery(Schedule.class);
        final Root<Schedule> toor = scheduleCriteriaQuery.from(Schedule.class);

        final CriteriaQuery<Refusal> query = criteriaBuilder.createQuery(Refusal.class);
        final Root<Refusal> root = query.from(Refusal.class);

        final CriteriaQuery<Expected> expectedCriteriaQuery = criteriaBuilder.createQuery(Expected.class);
        final Root<Expected> from = expectedCriteriaQuery.from(Expected.class);

        final CriteriaQuery<ScheduleCar> carCriteriaQuery = criteriaBuilder.createQuery(ScheduleCar.class);
        final Root<ScheduleCar> free = carCriteriaQuery.from(ScheduleCar.class);

        final StringBuilder stringBuilder = new StringBuilder();
        projects.forEach(s -> {
            scheduleCriteriaQuery.multiselect(toor.get("id"), toor.get("instructorName"))
                    .where(criteriaBuilder.equal(toor
                            .get("projectId"), s.getId()));
            final List<Schedule> schedules = session.createQuery(scheduleCriteriaQuery).getResultList();
            schedules.forEach(e -> stringBuilder.append(e.getInstructorName()).append(" "));
            s.setInstructors(stringBuilder.toString());
            stringBuilder.setLength(0);

            query.select(root).where(criteriaBuilder.equal(root
                    .get("projectId"), s.getId()));

            final List<Refusal> refusals = session.createQuery(query).getResultList();
            refusals.forEach(a -> stringBuilder.append(a.getInstructorNames()).append(" "));
            s.setRefusal(stringBuilder.toString());
            stringBuilder.setLength(0);

            expectedCriteriaQuery.select(from)
                    .where(criteriaBuilder.equal(from.get("projectId"), s.getId()));
            final List<Expected> expecteds = session.createQuery(expectedCriteriaQuery).getResultList();
            expecteds.forEach(f -> stringBuilder.append(f.getInstructorNames()).append(" "));
            s.setExpected(stringBuilder.toString());
            stringBuilder.setLength(0);

            carCriteriaQuery.select(free)
                    .where(criteriaBuilder.equal(free.get("projectId"), s.getId()));
            final List<ScheduleCar> cars = session.createQuery(carCriteriaQuery).getResultList();
            cars.forEach(c -> stringBuilder.append(c.getCarName()).append(" "));
            s.setCars(stringBuilder.toString());
            stringBuilder.setLength(0);
        });
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
        configureProject(projects, criteriaBuilder, session);
        session.getTransaction().commit();
        session.close();
        projects.sort(Comparator.comparing(Project::getId));
        return projects;
    }

    /**
     * @return {@link Session} for next steps.
     */
    private Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }
}
