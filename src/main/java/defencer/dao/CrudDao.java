package defencer.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

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
     * Searching entity by its id.
     *
     * @param id must not be null.
     * @return found entity by his id in database or {@literal null} if none found.
     */
    T findOne(ID id);

    /**
     * Deletes entity with given id.
     *
     * @param entity must not be {@literal null}.
     */
    void delete(ID entity);

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

    /**
     * @return list of found entity by given param and value.
     * @throws SQLException if were found none.
     */
    List<T> searchEntity(String param, String value) throws SQLException;
}
