package defencer.service.impl;

import defencer.dao.CrudDao;
import defencer.dao.impl.CrudDaoImpl;
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
public class CrudServiceImpl<T extends AbstractEntity> implements CrudService<T, Long> {

    private final CrudDao<T, Long> crudDao = new CrudDaoImpl<>();

    @Override

    public T createEntity(T entity) throws SQLException {
        return crudDao.save(entity);
    }

    @Override
    public void deleteEntity(Long id) throws SQLException {
        crudDao.delete(id);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public T updateEntity(T entity) throws SQLException {
        return crudDao.update(entity);
    }
}
