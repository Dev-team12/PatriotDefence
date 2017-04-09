package defencer.dao.factory;

import defencer.dao.InstructorDao;
import defencer.dao.impl.InstructorDaoImpl;

/**
 * @author Igor Gnes on 4/9/17.
 */
public class DaoFactory {

    public static InstructorDao getInstructorDao() {
        return new InstructorDaoImpl();
    }
}
