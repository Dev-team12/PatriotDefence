package defencer.dao.impl;

import defencer.dao.CrudDao;
import defencer.model.AbstractEntity;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Basic implementation of {@link CrudDao} interface.
 *
 * @param <T> param entity type.
 * @author Igor Gnes on 3/30/17.
 */
@NoArgsConstructor
public abstract class GrudDaoImpl<T extends AbstractEntity> implements CrudDao<T, Long> {


    /**
     * {@inheritDoc}.
     */
    @Override
    public T save(T entity) {
        // TODO add code for save and return that user
        return entity;
    }

    @Override
    public T findOne(Long id) {
        return null;
    }

    @Override
    public void delete(T entity) {

    }

    @Override
    public boolean exists(Long id) {
        return false;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public T update(T entity) {
        return null;
    }

    @Override
    public List<T> getEntityNames() {
        return null;
    }
}
