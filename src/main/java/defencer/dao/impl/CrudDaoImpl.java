package defencer.dao.impl;

import defencer.dao.CrudDao;
import defencer.model.AbstractEntity;
import defencer.util.HibernateUtil;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

/**
 * Basic implementation of {@link CrudDao} interface.
 *
 * @param <T> param entity type.
 * @author Igor Gnes on 3/30/17.
 */
@NoArgsConstructor
public class CrudDaoImpl<T extends AbstractEntity> implements CrudDao<T, Long> {

    private Session session = HibernateUtil.getSessionFactory().openSession();

    /**
     * {@inheritDoc}.
     */
    @Override
    public T save(T entity) {
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        HibernateUtil.shutdown();
        return entity;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public T findOne(Long id) {
        return null;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(Long id) {

    }

    /**
     * {@inheritDoc}.
     */
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

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<T> getEntityNames() {
        return null;
    }
}
