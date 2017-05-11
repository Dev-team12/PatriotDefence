package defencer.service.impl;

import defencer.dao.factory.DaoFactory;
import defencer.data.CurrentUser;
import defencer.model.*;
import defencer.service.WiseacreService;

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
    public List<Car> getFreeCar() {
        return DaoFactory.getWiseacreDao().getFreeCar();
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
    public List<Instructor> getFreeInstructors() {
        return DaoFactory.getWiseacreDao().getFreeInstructors();
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
        return DaoFactory.getWiseacreDao().getCarForAdminDashboard();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<AvailableProject> getProjectForAdminDashboard() {
        return DaoFactory.getWiseacreDao().getProjectForAdminDashboard();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void createCar(Car car) throws SQLException {
        super.createEntity(car);
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
    public void createProject(AvailableProject project) throws SQLException {
        super.createEntity(project);
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
        return DaoFactory.getWiseacreDao().getCurrentUser(email);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void updateCurrentUser(Long userId, String status) throws SQLException {
        DaoFactory.getWiseacreDao().updateCurrentUser(userId, status);
        final WorkDay workDay = new WorkDay();
        workDay.setDateOfCreation(LocalDate.now());
        workDay.setInstructorId(userId);
        final LocalDate projectDateStart = CurrentUser.getLink().getProjectDateStart();
        final LocalDate projectDateFinish = CurrentUser.getLink().getProjectDateFinish();
        workDay.setWorkDays(ChronoUnit.DAYS.between(projectDateStart, projectDateFinish));
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
    public List<Car> getCurrentCar(Long projectId) {
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
    public void deleteSelectedCar(Long carId) {
        DaoFactory.getWiseacreDao().deleteSelectedCar(carId);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void setFreeStatusForInstructorsByProjectId(Long projectId) {
        DaoFactory.getWiseacreDao().setFreeStatusForInstructorsByProjectId(projectId);
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
    public void updateSchedule(List<Instructor> instructors, Project project) {
        DaoFactory.getWiseacreDao().updateSchedule(instructors, project);
    }
}
