package defencer.dao.factory;

import defencer.dao.ApprenticeDao;
import defencer.dao.InstructorDao;
import defencer.dao.ProjectDao;
import defencer.dao.impl.ApprenticeDaoImpl;
import defencer.dao.impl.InstructorDaoImpl;
import defencer.dao.impl.ProjectDaoImpl;

/**
 * Own factory for dao interface.
 *
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
     * @return ApprenticeDao.
     */
    public static ApprenticeDao getApprenticeDao() {
        return new ApprenticeDaoImpl();
    }

    /**
     * @return ProjectDao.
     */
    public static ProjectDao getProjectDao() {
        return new ProjectDaoImpl();
    }
}
