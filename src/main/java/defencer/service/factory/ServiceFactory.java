package defencer.service.factory;

import defencer.service.ApprenticeService;
import defencer.service.InstructorService;
import defencer.service.ProjectService;
import defencer.service.impl.ApprenticeServiceImpl;
import defencer.service.impl.InstructorServiceImpl;
import defencer.service.impl.ProjectServiceImpl;

/**
 * Own factory for service interface.
 *
 * @author Igor Gnes on 4/9/17.
 */
public class ServiceFactory {

    /**
     * @return InstructorService.
     */
    public static InstructorService getInstructorService() {
        return new InstructorServiceImpl();
    }

    /**
     * @return ProjectService.
     */
    public static ProjectService getProjectService() {
        return new ProjectServiceImpl();
    }

    /**
     * @return ApprenticeService.
     */
    public static ApprenticeService getPupilService() {
        return new ApprenticeServiceImpl();
    }
}
