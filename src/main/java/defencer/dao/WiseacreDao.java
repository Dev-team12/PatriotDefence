package defencer.dao;

import defencer.data.CurrentUser;
import defencer.model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Gnes on 4/17/17.
 */
public interface WiseacreDao {

    /**
     * @return list of free {@link Car} for project.
     */
    List<Car> getFreeCar(Project project);

    /**
     * @return list of available {@link AvailableProject}.
     */
    List<AvailableProject> getAvailableProject();

    /**
     * @return list of free {@link Instructor} for project.
     */
    List<Instructor> getFreeInstructors(Project project);

    /**
     * @return map with project's name and times how often they were created for last months.
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
     * @return list with cars' names and id for admin dashboard.
     */
    List<Car> getCarForAdminDashboard();

    /**
     * @return list with projects' names and id for admin dashboard.
     */
    List<AvailableProject> getProjectForAdminDashboard();

    /**
     * @return {@link Instructor} like current user.
     */
    Instructor getCurrentUser(String email);

    /**
     * @param projectId is project's id.
     * @return list of instructors who were selected before.
     */
    List<Schedule> getCurrentInstructors(Long projectId) throws SQLException;

    /**
     * @param projectId is project's id.
     * @return list of car that were selected before.
     */
    List<ScheduleCar> getCurrentCar(Long projectId);

    /**
     * Delete instructor who was selected before.
     *
     * @param instructorId id instructor's id.
     */
    void deleteSelectedInstructors(Long instructorId, Long projectId);

    /**
     * Delete car that was selected before.
     *
     * @param carId id car's id.
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
     * Get days off statistic for instructors.
     */
    Map<String, Long> getDaysOffStatistic();

    /**
     * Delete car with given id.
     *
     * @param carId is car's id.
     */
    void deleteCar(Long carId);

    /**
     * @return list of events for calendar.
     */
    List<Event> getEventsToCalendar();

    /**
     * @return list of days off for calendar.
     */
    List<DaysOff> getDaysOffToCalendar();
}
