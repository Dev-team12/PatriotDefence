package defencer.service;

import defencer.model.Project;

/**
 * @author Igor Gnes on 5/6/17.
 */
public interface AdminReportService {

    /**
     * Report to admin about creating and deleting projects.
     *
     * @param project is created or deleted project.
     */
    void reportToAdmin(Project project);
}
