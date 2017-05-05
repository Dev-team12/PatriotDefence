package defencer.service.factory;

import defencer.service.*;
import defencer.service.impl.*;

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
     * @return instance of {@link EventService}.
     */
    public static EventService getEventService() {
        return new EventServiceImpl();
    }

    /**
     * @return instance of {@link WiseacreService}.
     */
    public static WiseacreService getWiseacreService() {
        return new WiseacreServiceImpl();
    }
}
