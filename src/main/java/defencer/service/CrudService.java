package defencer.service;

import defencer.model.Instructor;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * @param <T> is given entity.
 * @param <ID> entity identifier type.
 * @author Igor Gnes on 3/18/17.
 */
public interface CrudService<T, ID extends Serializable> {

    /**
     * Create a given entity.
     *
     * @param entity must not be {@literal null}.
     * @return the crated entity.
     */
    T createEntity(T entity) throws SQLException;

    /**
     * @param id entity's id in database, mustn't be {@literal null}.
     * @throws SQLException if something went wrong during deleting.
     */
    void deleteEntity(ID id) throws SQLException;

    /**
     * Update a given entity.
     *
     * @param entity must not be {@literal null}.
     */
    T updateEntity(T entity) throws SQLException;

    /**
     * @return list with instructors names.
     * @throws SQLException if found none.
     */
    default  List<Instructor> getInstructorsNames() throws SQLException {
        return null;
    }
}
