package defencer.service.impl;

import defencer.dao.CrudDao;
import defencer.model.AbstractEntity;
import defencer.service.CrudService;

import java.sql.SQLException;

/**
 * Basic implementation of {@link CrudService} interface.
 *
 * @param <T> entity type.
 *
 * @author Igor Gnes on 4/6/17.
 */
public abstract class CrudServiceImpl<T extends AbstractEntity> implements CrudService<T, Long> {

    private CrudDao<T, Long> crudDao;

    @Override

    public T createEntity(T entity) throws SQLException {
        return crudDao.save(entity);
    }

    @Override
    public void deleteEntity(Long id) throws SQLException {

    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public T updateEntity(T entity) throws SQLException {
        return crudDao.update(entity);
    }
}
