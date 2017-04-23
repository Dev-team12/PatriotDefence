package defencer.service.impl;

import defencer.dao.factory.DaoFactory;
import defencer.exception.entity.EntityAlreadyExistsException;
import defencer.model.Instructor;
import defencer.model.Project;
import defencer.service.InstructorService;
import lombok.val;

import java.sql.SQLException;
import java.util.List;

/**
 * Implementation of {@link InstructorService} interface.
 *
 * @author igor on 28.11.16.
 */
public class InstructorServiceImpl extends CrudServiceImpl<Instructor> implements InstructorService {

    /**
     * {@inheritDoc}.
     */
    @Override
    public Instructor createEntity(Instructor instructor) throws SQLException {
        if (!this.emailAvailable(instructor)) {
            throw new EntityAlreadyExistsException("Supplied email is already taken: " + instructor.getEmail());
        }
        return super.createEntity(instructor);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Project> findProjectByInstructor(Long id) {
        return DaoFactory.getInstructorDao().findProjectByInstructor(id);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Instructor findByEmail(String email) {
        return DaoFactory.getInstructorDao().findByEmail(email);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Instructor> getInstructors() {
        return DaoFactory.getInstructorDao().getInstructors();
    }

    /**
     * Checks if supplied email is already in the database.
     *
     * @param instructor to check email for.
     * @return true if email available, false otherwise.
     */
    private boolean emailAvailable(Instructor instructor) {
        val email = instructor.getEmail();
        return this.findByEmail(email) == null;
    }
}
