package defencer.dao;

import java.io.Serializable;

/**
 * @param <T> is given entity.
 * @param <ID> id id entity in database.
 * @author Igor Gnes on 3/30/17.
 */
public interface CrudDao<T, ID extends Serializable> {

    /**
     * Save a given entity.
     *
     * @param entity must not be null.
     * @return saved entity.
     */
    T save(T entity);

    /**
     * Deletes entity with given id.
     *
     * @param entity must not be {@literal null}.
     */
    void delete(T entity);

    /**
     * Check whether entity already exists.
     * 
     * @param id must not be {@literal null}.
     * @return {@literal true} if entity already exists and return {@literal false} if not.
     */
    boolean exists(ID id);

    /**
     * Updates a given entity.
     *
     * @param entity must not be {@literal null}.
     * @return updated entity.
     */
    T update(T entity);
}
