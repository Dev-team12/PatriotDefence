package defencer.dao;

import defencer.model.Instructor;
import defencer.model.Schedule;

import java.util.List;

/**
 * @author Igor Gnes on 3/30/17.
 */
public interface InstructorDao extends CrudDao<Instructor, Long> {

    /**
     * Fetches {@link Instructor} entity by provided email.
     * 
     * @param email instructor's email address, must not be {@literal null}.
     * @return {@link Instructor} entity associated with provided email, or {@literal null} if none found.
     */
    Instructor findByEmail(String email);

    /**
     * @return list of {@link Instructor}.
     */
    List<Instructor> getInstructors();

    /**
     * @return list of instructor who has role Chief Officer.
     */
    List<Instructor> findAdmins();

    /**
     * @return list of projects for current user.
     */
    List<Schedule> getMyProject(Long userId);

    /**
     * Change password for current user.
     */
    void changePassword(Long userId, String password);

    /**
     * Delete instructor with given id.
     *
     * @param instructorId is given instructor's id
     */
    void deleteInstructor(Long instructorId);
}
