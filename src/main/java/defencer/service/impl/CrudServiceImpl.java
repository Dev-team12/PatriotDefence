package defencer.service.impl;

import defencer.dao.CrudDao;
import defencer.dao.impl.CrudDaoImpl;
import defencer.model.AbstractEntity;
import defencer.service.CrudService;

import java.sql.SQLException;
import java.util.List;

/**
 * Basic implementation of {@link CrudService} interface.
 *
 * @param <T> entity type.
 *
 * @author Igor Gnes on 4/6/17.
 */
public class CrudServiceImpl<T extends AbstractEntity> implements CrudService<T, Long> {

    private final CrudDao<T, Long> crudDao = new CrudDaoImpl<>();

    /**
     * {@inheritDoc}.
     */
    @Override
    public T createEntity(T entity) throws SQLException {
        return crudDao.save(entity);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void deleteEntity(T entity) throws SQLException {
        crudDao.delete(entity);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public T updateEntity(T entity) throws SQLException {
        return crudDao.update(entity);
    }

    @Override
    public List<T> searchEntity(String param, String value) throws SQLException {
        return crudDao.searchEntity(param, value);
    }
}
