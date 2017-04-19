package defencer.dao.impl;

import defencer.dao.WiseacreDao;
import defencer.model.*;
import defencer.util.HibernateUtil;
import lombok.val;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.*;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Implementation of {@link WiseacreDao} interface.
 *
 * @author Igor Gnes on 4/17/17.
 */
public class WiseacreDaoImpl implements WiseacreDao {

    private Long workDay = 0L;
    private Long projectTimes = 0L;

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Car> getFreeCar() {
        final Session session = getCurrentSession();
        session.beginTransaction();
        val criteriaBuilder = session.getCriteriaBuilder();
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
        val criteriaBuilder = session.getCriteriaBuilder();
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
        val criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Instructor> availableProjectCriteriaQuery = criteriaBuilder.createQuery(Instructor.class);
        final Root<Instructor> root = availableProjectCriteriaQuery.from(Instructor.class);
        final List<Instructor> instructorList = session.createQuery(availableProjectCriteriaQuery
                .where(criteriaBuilder.equal(root.get("status"), "FREE"))).getResultList();
        session.getTransaction().commit();
        session.close();
        return instructorList;
    }

    public static void main(String[] args) {

        final WiseacreDaoImpl wiseacreDao = new WiseacreDaoImpl();
        wiseacreDao.getProjectStatistic();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Map<String, Long> getProjectStatistic() {
        final Session session = getCurrentSession();
        session.beginTransaction();
        val criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);

        final Root<Project> root = criteriaQuery.from(Project.class);
        final List<Project> projects = session.createQuery(criteriaQuery
                .where(criteriaBuilder.between(root.get("dateOfCreation"), LocalDate.now()
                        .minusMonths(1), LocalDate.now().plusDays(1)))).getResultList();

        val timesCriteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<ProjectTimes> timesDayCriteriaQuery = timesCriteriaBuilder
                .createQuery(ProjectTimes.class);
        final Root<ProjectTimes> toor = timesDayCriteriaQuery.from(ProjectTimes.class);

        Map<String, Long> projectStatistic = new HashMap<>();
        projects.forEach(s -> {
            timesDayCriteriaQuery.where(timesCriteriaBuilder.equal(toor.get("projectId"), s.getId()),
                    timesCriteriaBuilder.between(toor.get("dateOfCreation"), LocalDate.now()
                            .minusMonths(1), LocalDate.now().plusDays(1)));
            final List<ProjectTimes> resultList = session.createQuery(timesDayCriteriaQuery).getResultList();
            resultList.forEach(b -> projectTimes += b.getProjectTimes());
            projectStatistic.put(s.getName(), projectTimes);
            projectTimes = 0L;
        });
        System.out.println(projectStatistic.entrySet());
        return projectStatistic;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Map<String, Long> getInstructorStatistic() {
        final Session session = getCurrentSession();
        session.beginTransaction();
        val criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Instructor> criteriaQuery = criteriaBuilder.createQuery(Instructor.class);
        final Root<Instructor> root = criteriaQuery.from(Instructor.class);
        criteriaQuery.where(criteriaBuilder.isNotNull(root.get("projectId")));
        final List<Instructor> instructorList = session.createQuery(criteriaQuery).getResultList();

        val workCriteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<WorkDay> workDayCriteriaQuery = workCriteriaBuilder.createQuery(WorkDay.class);
        final Root<WorkDay> toor = workDayCriteriaQuery.from(WorkDay.class);

        Map<String, Long> instructorStatistic = new HashMap<>();

        instructorList.forEach(s -> {
            workDayCriteriaQuery.where(workCriteriaBuilder.equal(toor.get("instructorId"), s.getId()),
                    workCriteriaBuilder.between(toor.get("dateOfCreation"), LocalDate.now().minusMonths(1), LocalDate.now().plusDays(1)));
            final List<WorkDay> resultList = session.createQuery(workDayCriteriaQuery).getResultList();
            resultList.forEach(b -> workDay += b.getWorkDays());
            instructorStatistic.put(s.getFirstName(), workDay);
            workDay = 0L;
        });
        session.getTransaction().commit();
        session.close();
        return instructorStatistic;
    }

    /**
     * @return {@link Session} for next steps.
     */
    private Session getCurrentSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }
}
