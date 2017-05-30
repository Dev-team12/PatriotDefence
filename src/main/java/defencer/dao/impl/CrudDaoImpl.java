package defencer.dao.impl;

import defencer.dao.CrudDao;
import defencer.model.AbstractEntity;
import defencer.util.HibernateUtil;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

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
    public void delete(T entity) {
        final Session session = getSession();
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
        session.close();
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

    /**
     * @return {@link Session} for next steps.
     */
    private Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }
}
