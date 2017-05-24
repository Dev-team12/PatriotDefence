package defencer.service;

import defencer.model.Instructor;
import defencer.model.Project;
import defencer.model.Schedule;

import java.util.List;

/**
 * @author Igor Gnes on 4/6/17.
 */
public interface InstructorService extends CrudService<Instructor, Long> {

    /**
     * @param email instructor's email address.
     *
     * @return instructor with given email.
     */
    Instructor findByEmail(String email);


    /**
     * @return list of {@link Instructor}.
     */
    List<Instructor> getInstructors();

    /**
     * Configured project and send notifications to selected instructors.
     *
     * @param instructors is list of selected instructors.
     * @param project     is selected project
     */
    void configureProject(List<Instructor> instructors, Project project);

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
