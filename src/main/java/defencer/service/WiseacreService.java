package defencer.service;

import defencer.data.CurrentUser;
import defencer.model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Gnes on 4/17/17.
 */
public interface WiseacreService extends CrudService<AbstractEntity, Long> {

    /**
     * @return list of free car for project.
     */
    List<Car> getFreeCar();

    /**
     * @return list of available project.
     */
    List<String> getAvailableProject();

    /**
     * @return list of free instructors.
     */
    List<Instructor> getFreeInstructors(Project project);

    /**
     * @return map with project's name and times how often they were created.
     */
    Map<String, Integer> getProjectStatistic();

    /**
     * @return map with instructor's name and work days for last months.
     */
    Map<String, Long> getInstructorStatistic();

    /**
     * @return value of total instructors in Patriot Defence.
     */
    int getTotalInstructors();

    /**
     * @return value of total apprentice in Patriot Defence.
     */
    int getTotalApprentice();

    /**
     * @return value of created project for last months.
     */
    int getQuantityProjectForLastMonths();

    /**
     * @return value of added apprentice for last months.
     */
    int getQuantityApprenticeForLastMon();

    /**
     * @return list with cars' names for admin dashboard.
     */
    List<Car> getCarForAdminDashboard();

    /**
     * @return list with projects' names for admin dashboard.
     */
    List<AvailableProject> getProjectForAdminDashboard();

    /**
     * Create the given car.
     *
     * @param car going to be create.
     */
    void createCar(Car car) throws SQLException;

    /**
     * Delete the given car.
     *
     * @param entity going to be delete.
     */
    void deleteCar(Car entity) throws SQLException;

    /**
     * Create the given car.
     *
     * @param car going to be create.
     */
    void createProject(AvailableProject car) throws SQLException;

    /**
     * Delete the given project.
     *
     * @param project going to be delete.
     */
    void deleteProject(AvailableProject project) throws SQLException;

    /**
     * @return {@link Instructor} like current user.
     */
    Instructor getCurrentUser(String email);

    /**
     * @param userId is current user's userId.
     * @param status is new status for instructor.
     * @throws SQLException if can't update.
     */
    void updateCurrentUser(Long userId, String status) throws SQLException;

    /**
     * @param projectId is project's id.
     * @return list of instructors who were selected before.
     */
    List<Schedule> getCurrentInstructors(Long projectId);

    /**
     * @param projectId is project's id.
     * @return list of car that were selected before.
     */
    List<Car> getCurrentCar(Long projectId);

    /**
     * Delete instructor who was selected before.
     */
    void deleteSelectedInstructors(Long instructorId, Long projectId);

    /**
     * Delete car that was selected before.
     */
    void deleteSelectedCar(Long carId);

    /**
     * @param instructorId is instructor for who method get days off.
     * @return list of days off.
     */
    List<DaysOff> getDaysOff(Long instructorId);

    /**
     * Update expected by set instructor's id in project with given project id.
     */
    void updateExpected(List<Instructor> instructors, Project project);

    /**
     * Update schedule by set instructor's id.
     */
    void updateSchedule(CurrentUser currentUser, Schedule schedule);
}
