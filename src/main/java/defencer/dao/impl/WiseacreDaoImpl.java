package defencer.dao.impl;

import defencer.dao.WiseacreDao;
import defencer.data.CurrentUser;
import defencer.model.*;
import defencer.model.enums.Role;
import defencer.util.HibernateUtil;
import lombok.val;
import org.hibernate.Session;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.criteria.*;

/**
 * Implementation of {@link WiseacreDao} interface.
 *
 * @author Igor Gnes on 4/17/17.
 */
public class WiseacreDaoImpl extends CrudDaoImpl<AbstractEntity> implements WiseacreDao {

    private Long workDay = 0L;

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Car> getFreeCar(Project project) {
        final Session session = getCurrentSession();
        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        val query = criteriaBuilder.createQuery(ScheduleCar.class);
        Root<ScheduleCar> root = query.from(ScheduleCar.class);
        query.select(root);
        List<ScheduleCar> scheduleCars = session.createQuery(query).getResultList();

        val carCriteriaQuery = criteriaBuilder.createQuery(Car.class);
        val toor = carCriteriaQuery.from(Car.class);
        carCriteriaQuery.multiselect(toor.get("id"));
        List<Car> cars = session.createQuery(carCriteriaQuery).getResultList();

        Set<Long> freeCars = new HashSet<>();
        Set<Long> busyCars = new HashSet<>();
        Set<Long> absoluteFreeCars = new HashSet<>();
        scheduleCars.forEach(s -> {
            if ((project.getDateStart().isBefore(s.getProjectStart())
                    || project.getDateStart().isAfter(s.getProjectFinish()))
                    && (project.getDateFinish().isBefore(s.getProjectStart())
                    || project.getDateFinish().isAfter(s.getProjectFinish()))
                    && (!s.getProjectStart().isAfter(project.getDateStart())
                    || !s.getProjectStart().isBefore(project.getDateFinish()))) {
                freeCars.add(s.getCarId());
            } else {
                busyCars.add(s.getCarId());
            }
        });

        freeCars.forEach(s -> {
            if (!busyCars.contains(s)) {
                absoluteFreeCars.add(s);
            }
        });

        cars.forEach(s -> scheduleCars.forEach(v -> {
            if (s.getId().equals(v.getCarId())) {
                s.setId(-1L);
            }
        }));

        absoluteFreeCars.forEach(s -> {
            Car car = new Car();
            car.setId(s);
            cars.add(car);
        });

        List<Car> collect = cars.stream()
                .filter(s -> null != s.getId())
                .filter(s -> s.getId() != -1)
                .collect(Collectors.toList());

        final CriteriaQuery<Car> freeQuery = criteriaBuilder.createQuery(Car.class);
        final Root<Car> free = freeQuery.from(Car.class);

        cars.clear();
        collect.forEach(s -> {
            freeQuery.select(free)
                    .where(criteriaBuilder.equal(free.get("id"), s.getId()));
            final Car car = session.createQuery(freeQuery).getSingleResult();
            cars.add(car);
        });

        session.getTransaction().commit();
        session.close();
        return cars;
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

   /* public static void main(String[] args) {

        final WiseacreDaoImpl wiseacreDao = new WiseacreDaoImpl();
        final Project project = new Project();
        project.setDateStart(LocalDate.of(2017, 5, 24));
        project.setDateFinish(LocalDate.of(2017, 5, 26));
        List<Car> freeCar = wiseacreDao.getFreeCar(project);
        freeCar.forEach(s -> System.out.println(s.getCarName()));
//        final List<Instructor> freeInstructors = wiseacreDao.getFreeInstructors(project);

//        freeInstructors.forEach(s -> System.out.println(s.getFirstName()));
    }*/

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Instructor> getFreeInstructors(Project project) {
        final Session session = getCurrentSession();
        session.beginTransaction();

        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        final CriteriaQuery<Schedule> criteriaBuilderQuery = criteriaBuilder.createQuery(Schedule.class);
        final Root<Schedule> root = criteriaBuilderQuery.from(Schedule.class);
        criteriaBuilderQuery.multiselect(root.get("id"), root.get("instructorId"), root.get("startProject"), root.get("finishProject"));
        final List<Schedule> schedules = session.createQuery(criteriaBuilderQuery).getResultList();
        Set<Long> freeInstructorId = new HashSet<>();
        Set<Long> schedulesFree = new LinkedHashSet<>();
        Set<Long> daysOffFree = new LinkedHashSet<>();
        Set<Long> expectedFree = new LinkedHashSet<>();

        Set<Long> schedulesBusy = new LinkedHashSet<>();
        Set<Long> daysOffBusy = new LinkedHashSet<>();
        Set<Long> expectedBusy = new LinkedHashSet<>();

        schedules.forEach(s -> {
            if ((project.getDateStart().isBefore(s.getStartProject())
                    || project.getDateStart().isAfter(s.getFinishProject()))
                    && (project.getDateFinish().isBefore(s.getStartProject())
                    || project.getDateFinish().isAfter(s.getFinishProject()))
                    && (!s.getStartProject().isAfter(project.getDateStart())
                    || !s.getStartProject().isBefore(project.getDateFinish()))) {
                schedulesFree.add(s.getInstructorId());
            } else {
                schedulesBusy.add(s.getInstructorId());
            }
        });

        final CriteriaQuery<DaysOff> daysOffCriteriaQuery = criteriaBuilder.createQuery(DaysOff.class);
        final Root<DaysOff> toor = daysOffCriteriaQuery.from(DaysOff.class);
        daysOffCriteriaQuery.select(toor);
        final List<DaysOff> daysOffs = session.createQuery(daysOffCriteriaQuery).getResultList();

        daysOffs.forEach(s -> {
            if ((project.getDateStart().isBefore(s.getDateFrom())
                    || project.getDateStart().isAfter(s.getDateTo()))
                    && (project.getDateFinish().isBefore(s.getDateFrom())
                    || project.getDateFinish().isAfter(s.getDateTo()))
                    && (!s.getDateFrom().isAfter(project.getDateStart())
                    || !s.getDateFrom().isBefore(project.getDateFinish()))) {
                daysOffFree.add(s.getInstructorId());
            } else {
                daysOffBusy.add(s.getInstructorId());
            }
        });
        final CriteriaQuery<Expected> criteriaQuery = criteriaBuilder.createQuery(Expected.class);
        final Root<Expected> from = criteriaQuery.from(Expected.class);
        criteriaQuery.multiselect(from.get("id"), from.get("instructorId"), from.get("projectStart"),
                from.get("finishProject"));
        final List<Expected> expecteds = session.createQuery(criteriaQuery).getResultList();

        expecteds.forEach(s -> {
            if ((project.getDateStart().isBefore(s.getProjectStart())
                    || project.getDateStart().isAfter(s.getFinishProject()))
                    && (project.getDateFinish().isBefore(s.getProjectStart())
                    || project.getDateFinish().isAfter(s.getFinishProject()))
                    && (!s.getProjectStart().isAfter(project.getDateStart())
                    || !s.getProjectStart().isBefore(project.getDateFinish()))) {
                expectedFree.add(s.getInstructorId());
            } else {
                expectedBusy.add(s.getInstructorId());
            }
        });

        final CriteriaQuery<Instructor> instructorCriteriaQuery = criteriaBuilder.createQuery(Instructor.class);
        final Root<Instructor> linux = instructorCriteriaQuery.from(Instructor.class);
        instructorCriteriaQuery.multiselect(linux.get("id"))
                .where(criteriaBuilder.in(linux.get("role")).value(Role.COORDINATOR).value(Role.INSTRUCTOR));
        final List<Instructor> instructors = session.createQuery(instructorCriteriaQuery).getResultList();

        schedulesFree.forEach(s -> {
            if (!expectedBusy.contains(s) && !daysOffBusy.contains(s) && !schedulesBusy.contains(s)) {
                freeInstructorId.add(s);
            }
        });

        expectedFree.forEach(s -> {
            if (!schedulesBusy.contains(s) && !daysOffBusy.contains(s) && !expectedBusy.contains(s)) {
                freeInstructorId.add(s);
            }
        });

        daysOffFree.forEach(s -> {
            if (!schedulesBusy.contains(s) && !expectedBusy.contains(s) && !daysOffBusy.contains(s)) {
                freeInstructorId.add(s);
            }
        });

        instructors.forEach(v -> schedules.forEach(s -> {
            if (v.getId().equals(s.getInstructorId())) {
                v.setId(-1L);
            }
        }));

        instructors.forEach(v -> daysOffs.forEach(s -> {
            if (v.getId().equals(s.getInstructorId())) {
                v.setId(-1L);
            }
        }));

        instructors.forEach(s -> expecteds.forEach(r -> {
            if (s.getId().equals(r.getInstructorId())) {
                s.setId(-1L);
            }
        }));

        freeInstructorId.forEach(s -> {
            final Instructor instructor = new Instructor();
            instructor.setId(s);
            instructors.add(instructor);
        });

        final List<Instructor> collect = instructors.stream()
                .filter(s -> s.getId() != -1)
                .collect(Collectors.toList());
        List<Instructor> instructorList = new LinkedList<>();
        final CriteriaQuery<Instructor> query = criteriaBuilder.createQuery(Instructor.class);
        final Root<Instructor> free = query.from(Instructor.class);
        collect.forEach(s -> {
            query.select(free)
                    .where(criteriaBuilder.equal(free.get("id"), s.getId()));
            final Instructor instructor = session.createQuery(query).getSingleResult();
            instructorList.add(instructor);
        });
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
                .where(criteriaBuilder.in(root.get("role")).value(Role.INSTRUCTOR).value(Role.COORDINATOR));
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
     * 1* {@inheritDoc}.
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
        carCriteriaQuery.select(root);
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
    public List<Schedule> getCurrentInstructors(Long projectId) throws SQLException {
        final Session session = getCurrentSession();
        session.beginTransaction();
        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        val criteriaBuilderQuery = criteriaBuilder.createQuery(Schedule.class);
        Root<Schedule> root = criteriaBuilderQuery.from(Schedule.class);
        criteriaBuilderQuery.select(root)
                .where(criteriaBuilder.equal(root.get("projectId"), projectId));
        final List<Schedule> instructors = session.createQuery(criteriaBuilderQuery).getResultList();
        session.getTransaction().commit();
        session.close();
        return instructors;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<ScheduleCar> getCurrentCar(Long projectId) {
        final Session session = getCurrentSession();
        session.beginTransaction();
        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        val criteriaBuilderQuery = criteriaBuilder.createQuery(ScheduleCar.class);
        val root = criteriaBuilderQuery.from(ScheduleCar.class);
        criteriaBuilderQuery.select(root)
                .where(criteriaBuilder.equal(root.get("projectId"), projectId));
        List<ScheduleCar> scheduleCars = session.createQuery(criteriaBuilderQuery).getResultList();

        session.getTransaction().commit();
        session.close();
        return scheduleCars;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void deleteSelectedInstructors(Long instructorId, Long projectId) {
        final Session session = getCurrentSession();
        session.beginTransaction();
        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        val criteriaDelete = criteriaBuilder.createCriteriaDelete(Expected.class);
        Root<Expected> root = criteriaDelete.from(Expected.class);
        criteriaDelete.where(criteriaBuilder.equal(root.get("instructorId"), instructorId),
                criteriaBuilder.equal(root.get("projectId"), projectId));
        session.createQuery(criteriaDelete).executeUpdate();

        val query = criteriaBuilder.createCriteriaDelete(Schedule.class);
        final Root<Schedule> schedule = query.from(Schedule.class);
        query.where(criteriaBuilder.equal(schedule.get("instructorId"), instructorId),
                criteriaBuilder.equal(schedule.get("projectId"), projectId));
        session.createQuery(query).executeUpdate();

        final Refusal refusal = new Refusal();
        refusal.setInstructorId(instructorId);
        refusal.setProjectId(projectId);
        refusal.setInstructorNames(CurrentUser.getLink().getFirstName() + " " + CurrentUser.getLink().getLastName());
        super.save(refusal);

        session.getTransaction().commit();
        session.close();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void deleteSelectedCar(Long projectId, Long carId) {
        final Session session = getCurrentSession();
        session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        val criteriaDelete = criteriaBuilder.createCriteriaDelete(ScheduleCar.class);
        val root = criteriaDelete.from(ScheduleCar.class);
        criteriaDelete.where(criteriaBuilder.equal(root.get("projectId"), projectId), criteriaBuilder
                .equal(root.get("carId"), carId));

        session.createQuery(criteriaDelete).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<DaysOff> getDaysOff(Long instructorId) {
        final Session session = getCurrentSession();
        session.beginTransaction();
        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        val criteriaBuilderQuery = criteriaBuilder.createQuery(DaysOff.class);
        final Root<DaysOff> root = criteriaBuilderQuery.from(DaysOff.class);
        criteriaBuilderQuery.select(root)
                .where(criteriaBuilder.equal(root.get("instructorId"), instructorId));
        final List<DaysOff> daysOffList = session.createQuery(criteriaBuilderQuery).getResultList();
        session.getTransaction().commit();
        session.close();
        return daysOffList;
    }

    @Override
    public void updateSchedule(CurrentUser currentUser, Schedule schedule) {
        Session session = getCurrentSession();
        session.beginTransaction();

        schedule.setInstructorId(currentUser.getId());
        schedule.setInstructorName(currentUser.getFirstName() + " " + currentUser.getLastName());
        schedule.setStatus("Confirmed");
        save(schedule);

        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        val refusalCriteriaDelete = criteriaBuilder.createCriteriaDelete(Expected.class);
        final Root<Expected> refusal = refusalCriteriaDelete.from(Expected.class);

        refusalCriteriaDelete.where(criteriaBuilder.equal(refusal.get("instructorId"), currentUser.getId()),
                criteriaBuilder.equal(refusal.get("projectId"), schedule.getProjectId()));
        session.createQuery(refusalCriteriaDelete).executeUpdate();

        session.getTransaction().commit();
        session.close();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void updateExpected(List<Instructor> instructors, Project project) {
        Session session = getCurrentSession();
        session.beginTransaction();

        final Expected expected = new Expected();
        expected.setProjectName(project.getNameId());
        expected.setProjectId(project.getId());
        expected.setProjectStart(project.getDateStart());
        expected.setFinishProject(project.getDateFinish());
        expected.setStatus("Need confirm");
        instructors.forEach(s -> {
            expected.setInstructorId(s.getId());
            expected.setInstructorNames(s.getFirstLastName());
            save(expected);
        });

        final CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        final CriteriaDelete<Refusal> refusalCriteriaDelete = criteriaBuilder.createCriteriaDelete(Refusal.class);
        final Root<Refusal> refusal = refusalCriteriaDelete.from(Refusal.class);

        instructors.forEach(s -> {
            refusalCriteriaDelete.where(criteriaBuilder.equal(refusal.get("instructorId"), s.getId()),
                    criteriaBuilder.equal(refusal.get("projectId"), project.getId()));
            session.createQuery(refusalCriteriaDelete).executeUpdate();
        });

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
