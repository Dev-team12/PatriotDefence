package defencer.dao;

import defencer.model.Instructor;
import defencer.model.Project;

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
     * Fetches {@link Instructor} entity by provided phone.
     *
     * @param phone instructor's phone number, must not be {@literal null}.
     * @return {@link Instructor} entity associated with provided phone, or {@literal null} if none found.
     */
    Instructor findByPhone(String phone);

    /**
     * @param id instructor's id in database, must not be {@literal null}
     * @return project with given instructor.
     */
    Project findProjectByInstructor(Long id);

    /**
     * @return list of {@link Instructor}.
     */
    List<Instructor> getInstructors();

    /**
     * @return list of instructor who has role Chief Officer.
     */
    List<Instructor> findAdmins();
}
