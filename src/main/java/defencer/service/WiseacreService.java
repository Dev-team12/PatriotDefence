package defencer.service;

import defencer.data.CurrentUser;
import defencer.model.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Gnes on 4/17/17.
 */
public interface WiseacreService extends CrudService<AbstractEntity, Long> {

    /**
     * @return list of free car for project.
     */
    List<Car> getFreeCar(Project project);

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
     * Create new car with given name.
     */
    void createCar(String carName) throws SQLException;

    /**
     * Delete car with given id.
     *
     * @param carId is car's unique id.
     */
    void deleteCar(Long carId) throws SQLException;

    /**
     * Create the given car.
     */
    void createProject(String projectName) throws SQLException;

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
     * @throws SQLException if can't update.
     */
    void setWorksDays(Long userId, LocalDate startProject, LocalDate finishProject) throws SQLException;

    /**
     * @param projectId is project's id.
     * @return list of instructors who were selected before.
     */
    List<Schedule> getCurrentInstructors(Long projectId);

    /**
     * @param projectId is project's id.
     * @return list of car that were selected before.
     */
    List<ScheduleCar> getCurrentCar(Long projectId);

    /**
     * Delete instructor who was selected before.
     */
    void deleteSelectedInstructors(Long instructorId, Long projectId);

    /**
     * Delete car that was selected before.
     */
    void deleteSelectedCar(Long projectId, Long carId);

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

    /**
     * Add new days off for current user.
     */
    void addNewDaysOff(Long userId, LocalDate from, LocalDate to, List<Schedule> items);

    /**
     * Update schedule car by set to there project and car id.
     */
    void updateScheduleCar(Project project, Car car);

    /**
     * Get days off statistic for instructors.
     */
    Map<String, Long> getDaysOffStatistic();
}
