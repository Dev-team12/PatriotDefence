package defencer.dao.factory;

import defencer.dao.InstructorDao;
import defencer.dao.PupilDao;
import defencer.dao.impl.InstructorDaoImpl;
import defencer.dao.impl.PupilDaoImpl;

/**
 * @author Igor Gnes on 4/9/17.
 */
public class DaoFactory {

    /**
     * @return InstructorDao.
     */
    public static InstructorDao getInstructorDao() {
        return new InstructorDaoImpl();
    }

    /**
     * @return PupilDao.
     */
    public static PupilDao getPupilDao() {
        return new PupilDaoImpl();
    }
}
