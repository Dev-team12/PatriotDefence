package defencer.service;

import defencer.model.Instructor;
import defencer.model.Project;

import java.util.List;

/**
 * @author Igor Gnes on 4/6/17.
 */
public interface InstructorService extends CrudService<Instructor, Long> {

    /**
     * @param id instructor's id in database, must not be {@literal null}
     * @return project with given instructor.
     */
    Project findProjectByInstructor(Long id);

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
}
