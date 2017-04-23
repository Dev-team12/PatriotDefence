package defencer.dao.factory;

import defencer.dao.*;
import defencer.dao.impl.*;

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
