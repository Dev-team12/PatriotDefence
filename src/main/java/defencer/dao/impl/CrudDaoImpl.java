package defencer.dao.impl;

import defencer.dao.CrudDao;
import defencer.model.AbstractEntity;
import defencer.util.HibernateUtil;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

/**
 * Basic implementation of {@link CrudDao} interface.
 *
 * @param <T> param entity type.
 * @author Igor Gnes on 3/30/17.
 */
@NoArgsConstructor
public class CrudDaoImpl<T extends AbstractEntity> implements CrudDao<T, Long> {

    /**
     * {@inheritDoc}.
     */
    @Override
    public T save(T entity) {
        final Session session = getSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();
        return entity;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public T findOne(Long id) {
        final Session session = getSession();
        return null;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void delete(Long id) {
        final Session session = getSession();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public boolean exists(Long id) {
        final Session session = getSession();
        return false;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public T update(T entity) {
        final Session session = getSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
        return entity;
    }

    @Override
    public List<T> searchEntity(String param, String value) throws SQLException {
        final Session session = getSession();
        return null;
    }

    private Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }
}
