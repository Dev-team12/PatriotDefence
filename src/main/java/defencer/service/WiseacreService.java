package defencer.service;

import defencer.model.AvailableProject;
import defencer.model.Car;
import defencer.model.Instructor;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Gnes on 4/17/17.
 */
public interface WiseacreService {

    /**
     * @return list of free car for project.
     */
    List<String> getFreeCar();

    /**
     * @return list of available project.
     */
    List<String> getAvailableProject();

    /**
     * @return list of free instructors.
     */
    List<Instructor> getFreeInstructors();

    /**
     * @return map with project's name and times how often they were created.
     */
    Map<String, Long> getProjectStatistic();

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
     * @param id is current user's id.
     * @param status is new status for instructor.
     * @throws SQLException if can't update.
     */
    void updateCurrentUser(Long id, String status) throws SQLException;
}
