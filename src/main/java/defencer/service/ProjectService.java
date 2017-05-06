package defencer.service;

import defencer.model.Project;

import java.util.List;

/**
 * @author Igor Gnes on 4/6/17.
 */
public interface ProjectService extends CrudService<Project, Long> {

    /**
     * @return list of project by period, default is six months.
     */
    List<Project> findByPeriod();

    /**
     * @return list of project with parameters search.
     */
    List<Project> getFindProject(Long periodInDays, String projectName);

    /**
     * @param project is project that going to be closed.
     */
    void closeProject(Project project);
}
