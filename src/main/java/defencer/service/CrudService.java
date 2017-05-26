package defencer.service;

import java.io.Serializable;
import java.sql.SQLException;

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
     * @param entity mustn't be {@literal null}.
     * @throws SQLException if something went wrong during deleting.
     */
    void deleteEntity(T entity) throws SQLException;

    /**
     * Update a given entity.
     *
     * @param entity must not be {@literal null}.
     */
    T updateEntity(T entity) throws SQLException;
}
