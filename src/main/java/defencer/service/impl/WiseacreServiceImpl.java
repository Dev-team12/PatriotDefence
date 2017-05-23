package defencer.service.impl;

import defencer.dao.factory.DaoFactory;
import defencer.data.CurrentUser;
import defencer.model.*;
import defencer.service.CryptoService;
import defencer.service.WiseacreService;
import defencer.service.cryptography.CryptoCar;
import defencer.service.cryptography.CryptoInstructor;
import defencer.service.cryptography.CryptoProjectTypes;
import defencer.service.factory.ServiceFactory;
import defencer.util.NotificationUtil;
import lombok.val;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link WiseacreService} interface.
 *
 * @author Igor Gnes on 4/17/17.
 */
public class WiseacreServiceImpl extends CrudServiceImpl<AbstractEntity> implements  WiseacreService {

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Car> getFreeCar(Project project) {
        final List<Car> encryptedCars = DaoFactory.getWiseacreDao().getFreeCar(project);
        CryptoService<Car> cryptoService = new CryptoCar();
        return cryptoService.decryptEntityList(encryptedCars);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<String> getAvailableProject() {
        List<String> availableProject = new LinkedList<>();
        availableProject.add("");
        DaoFactory.getWiseacreDao().getAvailableProject().forEach(s -> availableProject.add(s.getProjectName()));
        return availableProject;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Instructor> getFreeInstructors(Project project) {
        CryptoService<Instructor> cryptoService = new CryptoInstructor();
        val decryptedInstructors = DaoFactory.getWiseacreDao().getFreeInstructors(project);
        return cryptoService.decryptEntityList(decryptedInstructors);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Map<String, Integer> getProjectStatistic() {
        return DaoFactory.getWiseacreDao().getProjectStatistic();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Map<String, Long> getInstructorStatistic() {
        return DaoFactory.getWiseacreDao().getInstructorStatistic();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int getTotalInstructors() {
        return DaoFactory.getWiseacreDao().getTotalInstructors();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int getTotalApprentice() {
        return DaoFactory.getWiseacreDao().getTotalApprentice();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int getQuantityProjectForLastMonths() {
        return DaoFactory.getWiseacreDao().getQuantityProjectForLastMonths();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int getQuantityApprenticeForLastMon() {
        return DaoFactory.getWiseacreDao().getQuantityApprenticeForLastMon();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Car> getCarForAdminDashboard() {
        final List<Car> encryptedCars = DaoFactory.getWiseacreDao().getCarForAdminDashboard();
        CryptoService<Car> cryptoService = new CryptoCar();
        return cryptoService.decryptEntityList(encryptedCars);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<AvailableProject> getProjectForAdminDashboard() {
        val encryptedProject = DaoFactory.getWiseacreDao().getProjectForAdminDashboard();
        CryptoService<AvailableProject> cryptoService = new CryptoProjectTypes();
        return cryptoService.decryptEntityList(encryptedProject);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void createCar(String carName) throws SQLException {
        final Car car = new Car();
        car.setCarName(carName);
        CryptoService<Car> cryptoService = new CryptoCar();
        super.createEntity(cryptoService.encryptEntity(car));
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void deleteCar(Car car) throws SQLException {
        super.deleteEntity(car);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void createProject(String projectName) throws SQLException {
        final AvailableProject availableProject = new AvailableProject();
        availableProject.setProjectName(projectName);
        CryptoService<AvailableProject> cryptoService = new CryptoProjectTypes();
        super.createEntity(cryptoService.encryptEntity(availableProject));
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void deleteProject(AvailableProject project) throws SQLException {
        super.deleteEntity(project);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Instructor getCurrentUser(String email) {
        CryptoService<Instructor> cryptoService = new CryptoInstructor();
        final Instructor currentUser = DaoFactory.getWiseacreDao().getCurrentUser(email);
        return cryptoService.decryptEntity(currentUser);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void setWorksDays(Long userId, LocalDate startProject, LocalDate finishProject) throws SQLException {
        final WorkDay workDay = new WorkDay();
        workDay.setDateOfCreation(LocalDate.now());
        workDay.setInstructorId(userId);
        workDay.setWorkDays(ChronoUnit.DAYS.between(startProject, finishProject));
        super.createEntity(workDay);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Schedule> getCurrentInstructors(Long projectId) {
        try {
            return DaoFactory.getWiseacreDao().getCurrentInstructors(projectId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<ScheduleCar> getCurrentCar(Long projectId) {
        return DaoFactory.getWiseacreDao().getCurrentCar(projectId);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void deleteSelectedInstructors(Long instructorId, Long projectId) {
        DaoFactory.getWiseacreDao().deleteSelectedInstructors(instructorId, projectId);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void deleteSelectedCar(Long projectId, Long carId) {
        DaoFactory.getWiseacreDao().deleteSelectedCar(projectId, carId);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<DaysOff> getDaysOff(Long instructorId) {
        return DaoFactory.getWiseacreDao().getDaysOff(instructorId);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void updateExpected(List<Instructor> instructors, Project project) {
        DaoFactory.getWiseacreDao().updateExpected(instructors, project);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void updateSchedule(CurrentUser currentUser, Schedule schedule) {
        DaoFactory.getWiseacreDao().updateSchedule(currentUser, schedule);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void addNewDaysOff(Long userId, LocalDate from, LocalDate to, List<Schedule> myProjects) {
        final DaysOff daysOff = new DaysOff();
        daysOff.setInstructorId(userId);
        daysOff.setDateFrom(from);
        daysOff.setDateTo(to);
        final List<Boolean> possibleToGo = possibleToGo(daysOff, myProjects);
        if (possibleToGo.contains(false)) {
            NotificationUtil.warningAlert("Wrong", "unfortunately you can't take a day off"
                    + " because you have a project in this time", NotificationUtil.SHORT);
            return;
        }
        saveDaysOff(daysOff);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void updateScheduleCar(Project project, Car car) {
        final ScheduleCar scheduleCar = new ScheduleCar();
        scheduleCar.setCarId(car.getId());
        scheduleCar.setCarName(car.getCarName());
        scheduleCar.setProjectId(project.getId());
        scheduleCar.setProjectStart(project.getDateStart());
        scheduleCar.setProjectFinish(project.getDateFinish());
        try {
            createEntity(scheduleCar);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Map<String, Long> getDaysOffStatistic() {
        return DaoFactory.getWiseacreDao().getDaysOffStatistic();
    }

    /**
     * Check that current instructors doesn't have a project in selected time.
     */
    private List<Boolean> possibleToGo(DaysOff daysOff, List<Schedule> myProjects) {
        List<Boolean> possible = new LinkedList<>();
        myProjects.forEach(s -> {
            if ((s.getStartProject().isBefore(daysOff.getDateFrom())
                    || s.getStartProject().isAfter(daysOff.getDateTo()))
                    && (s.getFinishProject().isBefore(daysOff.getDateFrom())
                    || s.getFinishProject().isAfter(daysOff.getDateTo()))
                    && (!daysOff.getDateFrom().isAfter(s.getStartProject())
                    || !daysOff.getDateFrom().isBefore(s.getFinishProject()))) {
                possible.add(true);
            } else {
                possible.add(false);
            }
        });
        return possible;
    }

    /**
     * Whether Success add days off to database.
     */
    private void saveDaysOff(DaysOff daysOff) {
        try {
            ServiceFactory.getWiseacreService().createEntity(daysOff);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
