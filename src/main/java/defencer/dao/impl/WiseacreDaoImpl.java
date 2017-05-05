package defencer.dao.impl;

import defencer.dao.WiseacreDao;
import defencer.model.*;
import defencer.util.HibernateUtil;
import lombok.val;
import org.hibernate.Session;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

/**
 * Implementation of {@link WiseacreDao} interface.
 *
 * @author Igor Gnes on 4/17/17.
 */
public class WiseacreDaoImpl extends CrudDaoImpl<AbstractEntity> implements WiseacreDao  {

    private Long workDay = 0L;

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
        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Instructor> criteriaQuery = criteriaBuilder.createQuery(Instructor.class);
        final Root<Instructor> root = criteriaQuery.from(Instructor.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("status"), "FREE"));
        final List<Instructor> instructorList = session.createQuery(criteriaQuery).getResultList();
        session.getTransaction().commit();
        session.close();
        return instructorList;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Map<String, Integer> getProjectStatistic() {
        final Session session = getCurrentSession();
        session.beginTransaction();
        val criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);

        final Root<Project> root = criteriaQuery.from(Project.class);
        criteriaQuery.multiselect(root.get("id"), root.get("name"));
        final List<Project> projects = session.createQuery(criteriaQuery
                .where(criteriaBuilder.between(root.get("dateOfCreation"), LocalDate.now()
                        .minusMonths(1), LocalDate.now().plusDays(1)))).getResultList();

        val timesCriteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<ProjectTimes> timesDayCriteriaQuery = timesCriteriaBuilder
                .createQuery(ProjectTimes.class);
        final Root<ProjectTimes> toor = timesDayCriteriaQuery.from(ProjectTimes.class);
        Map<String, Integer> projectStatistic = new HashMap<>();

        projects.forEach(s -> {
            timesDayCriteriaQuery.multiselect(toor.get("id"))
                    .where(timesCriteriaBuilder.equal(toor.get("projectName"), s.getName()),
                            timesCriteriaBuilder.between(toor.get("dateOfCreation"), LocalDate.now()
                                    .minusMonths(1), LocalDate.now().plusDays(1)));
            final List<ProjectTimes> resultList = session.createQuery(timesDayCriteriaQuery).getResultList();
            projectStatistic.put(s.getName(), resultList.size());
        });
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
        criteriaQuery.multiselect(root.get("id"), root.get("firstName"), root.get("lastName"))
                .where(criteriaBuilder.isNotNull(root.get("projectId")));
        final List<Instructor> instructorList = session.createQuery(criteriaQuery).getResultList();

        val workCriteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<WorkDay> workDayCriteriaQuery = workCriteriaBuilder.createQuery(WorkDay.class);
        final Root<WorkDay> toor = workDayCriteriaQuery.from(WorkDay.class);

        Map<String, Long> instructorStatistic = new HashMap<>();
        instructorList.forEach(s -> {
            workDayCriteriaQuery.multiselect(toor.get("id"), toor.get("workDays"))
                    .where(workCriteriaBuilder.equal(toor.get("instructorId"), s.getId()),
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
     * {@inheritDoc}.
     */
    @Override
    public int getTotalInstructors() {
        final Session session = getCurrentSession();
        session.beginTransaction();
        val criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Instructor> apprenticeCriteriaQuery = criteriaBuilder.createQuery(Instructor.class);
        final Root<Instructor> root = apprenticeCriteriaQuery.from(Instructor.class);
        apprenticeCriteriaQuery.select(root.get("id"));
        final int totalInstructors = session.createQuery(apprenticeCriteriaQuery).getResultList().size();
        session.getTransaction().commit();
        session.close();
        return totalInstructors;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int getTotalApprentice() {
        final Session session = getCurrentSession();
        session.beginTransaction();
        val criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Apprentice> apprenticeCriteriaQuery = criteriaBuilder.createQuery(Apprentice.class);
        final Root<Apprentice> root = apprenticeCriteriaQuery.from(Apprentice.class);
        apprenticeCriteriaQuery.select(root.get("id"));
        final int totalApprentices = session.createQuery(apprenticeCriteriaQuery).getResultList().size();
        session.getTransaction().commit();
        session.close();
        return totalApprentices;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int getQuantityProjectForLastMonths() {
        final Session session = getCurrentSession();
        session.beginTransaction();
        val criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Project> quantityProjectQuery = criteriaBuilder.createQuery(Project.class);
        final Root<Project> root = quantityProjectQuery.from(Project.class);
        quantityProjectQuery.select(root.get("id")).where(criteriaBuilder.between(root.get("dateOfCreation"), LocalDate.now().minusMonths(1), LocalDate.now().plusDays(1)));
        final int quantity = session.createQuery(quantityProjectQuery).getResultList().size();
        session.getTransaction().commit();
        session.close();
        return quantity;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int getQuantityApprenticeForLastMon() {
        final Session session = getCurrentSession();
        session.beginTransaction();
        val criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Apprentice> quantityApprenticeQuery = criteriaBuilder.createQuery(Apprentice.class);
        final Root<Apprentice> root = quantityApprenticeQuery.from(Apprentice.class);
        quantityApprenticeQuery.select(root.get("id")).where(criteriaBuilder.between(root.get("dateOfAdded"), LocalDate.now().minusMonths(1), LocalDate.now().plusDays(1)));
        final int quantity = session.createQuery(quantityApprenticeQuery).getResultList().size();
        session.getTransaction().commit();
        session.close();
        return quantity;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Car> getCarForAdminDashboard() {
        final Session session = getCurrentSession();
        session.beginTransaction();
        val criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Car> carCriteriaQuery = criteriaBuilder.createQuery(Car.class);
        final Root<Car> root = carCriteriaQuery.from(Car.class);
        carCriteriaQuery.multiselect(root.get("id"), root.get("carName"), root.get("status"));
        final List<Car> carList = session.createQuery(carCriteriaQuery).getResultList();
        session.getTransaction().commit();
        session.close();
        return carList;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<AvailableProject> getProjectForAdminDashboard() {
        final Session session = getCurrentSession();
        session.beginTransaction();
        val criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<AvailableProject> projectCriteriaQuery = criteriaBuilder.createQuery(AvailableProject.class);
        final Root<AvailableProject> root = projectCriteriaQuery.from(AvailableProject.class);
        projectCriteriaQuery.multiselect(root.get("id"), root.get("projectName"));
        final List<AvailableProject> availableProjectList = session.createQuery(projectCriteriaQuery).getResultList();
        session.getTransaction().commit();
        session.close();
        return availableProjectList;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Instructor getCurrentUser(String email) {
        final Session session = getCurrentSession();
        session.beginTransaction();
        val criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Instructor> currentUser = criteriaBuilder.createQuery(Instructor.class);
        final Root<Instructor> root = currentUser.from(Instructor.class);
        currentUser.select(root)
                .where(criteriaBuilder.equal(root.get("email"), email));
        final Instructor instructor = session.createQuery(currentUser).getSingleResult();

        session.getTransaction().commit();
        session.close();
        return instructor;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void updateCurrentUser(Long userId, String status) throws SQLException {
        final Session session = getCurrentSession();
        session.beginTransaction();

        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaUpdate<Instructor> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Instructor.class);
        final Root<Instructor> root = criteriaUpdate.from(Instructor.class);
        if ("FREE".equals(status)) {
            criteriaUpdate.set(root.get("status"), status)
                    .set(root.get("projectId"), -1)
                    .where(criteriaBuilder.equal(root.get("id"), userId));
        } else {
            criteriaUpdate.set(root.get("status"), status)
                    .where(criteriaBuilder.equal(root.get("id"), userId));
        }
        session.createQuery(criteriaUpdate).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Instructor> getCurrentInstructors(Long projectId) throws SQLException {
        final Session session = getCurrentSession();
        session.beginTransaction();

        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Instructor> criteriaBuilderQuery = criteriaBuilder.createQuery(Instructor.class);
        final Root<Instructor> root = criteriaBuilderQuery.from(Instructor.class);
        criteriaBuilderQuery.select(root)
                .where(criteriaBuilder.equal(root.get("projectId"), projectId));
        final List<Instructor> instructors = session.createQuery(criteriaBuilderQuery).getResultList();

        session.getTransaction().commit();
        session.close();

        return instructors;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void deleteSelectedInstructors(Long instructorId) {
        final Session session = getCurrentSession();
        session.beginTransaction();

        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        val criteriaUpdateQuery = criteriaBuilder.createCriteriaUpdate(Instructor.class);
        final Root<Instructor> root = criteriaUpdateQuery.from(Instructor.class);
        criteriaUpdateQuery.set(root.get("status"), "FREE").set(root.get("projectId"), -1)
                .where(criteriaBuilder.equal(root.get("id"), instructorId));
        session.createQuery(criteriaUpdateQuery).executeUpdate();

        session.getTransaction().commit();
        session.close();
    }


    /**
     * {@inheritDoc}.
     */
    @Override
    public void setFreeStatusForInstructorsByProjectId(Long projectId) {
        final Session session = getCurrentSession();
        session.beginTransaction();

        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        val criteriaBuilderQuery = criteriaBuilder.createCriteriaUpdate(Instructor.class);
        final Root<Instructor> root = criteriaBuilderQuery.from(Instructor.class);
        criteriaBuilderQuery.set(root.get("status"), "FREE").set(root.get("projectId"), -1)
                .where(criteriaBuilder.equal(root.get("projectId"), projectId));
        session.createQuery(criteriaBuilderQuery).executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    /**
     * @return {@link Session} for next steps.
     */
    private Session getCurrentSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }
}
