package defencer.dao;

import defencer.model.AvailableProject;
import defencer.model.Car;
import defencer.model.Instructor;

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
    List<Car> getFreeCar();

    /**
     * @return list of available {@link AvailableProject}.
     */
    List<AvailableProject> getAvailableProject();

    /**
     * @return list of free {@link Instructor} for project.
     */
    List<Instructor> getFreeInstructors();

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
     * @param userId is current user's userId.
     * @param status is new status for instructor.
     * @throws SQLException if can't update.
     */
    void updateCurrentUser(Long userId, String status) throws SQLException;

    /**
     * @param projectId is project's id.
     * @return list of instructors who were selected before.
     */
    List<Instructor> getCurrentInstructors(Long projectId) throws SQLException;

    /**
     * Delete instructor who was selected before.
     * @param instructorId id instructor's id.
     */
    void deleteSelectedInstructors(Long instructorId);
}
