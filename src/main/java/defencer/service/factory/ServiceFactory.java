package defencer.service.factory;

import defencer.service.InstructorService;
import defencer.service.ProjectService;
import defencer.service.PupilService;
import defencer.service.impl.InstructorServiceImpl;
import defencer.service.impl.ProjectServiceImpl;
import defencer.service.impl.PupilServiceImpl;

/**
 * @author Igor Gnes on 4/9/17.
 */
public class ServiceFactory {

    public static InstructorService getInstructorService() {
        return new InstructorServiceImpl();
    }

    public static ProjectService getProjectService() {
        return new ProjectServiceImpl();
    }

    public static PupilService getPupilService() {
        return new PupilServiceImpl();
    }
}
