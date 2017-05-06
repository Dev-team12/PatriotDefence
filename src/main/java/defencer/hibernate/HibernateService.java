package defencer.hibernate;

import defencer.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * @author Nikita on 16.04.2017.
 */
public class HibernateService {


    /**
     * Executing query.
     */
    public static List<?> executeQuery(String stringQuery) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery(stringQuery);

        transaction.commit();
        session.close();

        return query.list();
    }


    /**
     * Executing query 4m HibernateQueryBuilder.
     */
    public static List<?> executeQuery(HibernateQueryBuilder hibernateQueryBuilder) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        List<?> resultList = session.createQuery(hibernateQueryBuilder.getQuery()).getResultList();

        transaction.commit();
        session.close();

        return resultList;
    }
}
