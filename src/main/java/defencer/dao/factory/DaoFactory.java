package defencer.dao.factory;

import defencer.dao.ApprenticeDao;
import defencer.dao.InstructorDao;
import defencer.dao.ProjectDao;
import defencer.dao.WiseacreDao;
import defencer.dao.impl.ApprenticeDaoImpl;
import defencer.dao.impl.InstructorDaoImpl;
import defencer.dao.impl.ProjectDaoImpl;
import defencer.dao.impl.WiseacreDaoImpl;

/**
 * Own factory for dao interface.
 *
 * @author Igor Gnes on 4/9/17.
 */
public class DaoFactory {


    /**
     * @return instance of {@link InstructorDao}.
     */
    public static InstructorDao getInstructorDao() {
        return new InstructorDaoImpl();
    }


    /**
     * @return instance of {@link ApprenticeDao}.
     */
    public static ApprenticeDao getApprenticeDao() {
        return new ApprenticeDaoImpl();
    }


    /**
     * @return instance of {@link ProjectDao}.
     */
    public static ProjectDao getProjectDao() {
        return new ProjectDaoImpl();
    }

    /**
     * @return instance of {@link WiseacreDao}.
     */
    public static WiseacreDao getWiseacreDao() {
        return new WiseacreDaoImpl();
    }
}
