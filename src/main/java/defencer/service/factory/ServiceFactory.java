package defencer.service.factory;

import defencer.service.ApprenticeService;
import defencer.service.InstructorService;
import defencer.service.ProjectService;
import defencer.service.WiseacreService;
import defencer.service.impl.ApprenticeServiceImpl;
import defencer.service.impl.InstructorServiceImpl;
import defencer.service.impl.ProjectServiceImpl;
import defencer.service.impl.WiseacreServiceImpl;

/**
 * Own factory for service interface.
 *
 * @author Igor Gnes on 4/9/17.
 */
public class ServiceFactory {

    /**
     * @return instance of {@link InstructorService}.
     */
    public static InstructorService getInstructorService() {
        return new InstructorServiceImpl();
    }

    /**
     * @return instance of {@link ProjectService}.
     */
    public static ProjectService getProjectService() {
        return new ProjectServiceImpl();
    }

    /**
     * @return instance of {@link ApprenticeService}.
     */
    public static ApprenticeService getApprenticeService() {
        return new ApprenticeServiceImpl();
    }

    /**
     * @return instance of {@link WiseacreService}.
     */
    public static WiseacreService getWiseacreService() {
        return new WiseacreServiceImpl();
    }
}
