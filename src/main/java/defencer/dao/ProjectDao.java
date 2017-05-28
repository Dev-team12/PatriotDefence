package defencer.dao;

import defencer.model.Project;

import java.util.List;

/**
 * @author Igor Gnes on 4/10/17.
 */
public interface ProjectDao extends CrudDao<Project, Long> {

    /**
     * @param defaultPeriod is 30 days.
     * @return list of entity for given period, default is six months.
     */
    List<Project> getProjectForGivenPeriod(Long defaultPeriod);

    /**
     * @return list of project with parameters search.
     */
    List<Project> getFindProject(Long periodInDays, String projectName);

    /**
     * Delete project with given id.
     *
     * @param projectId is given project's id
     */
    void deleteProject(Long projectId);

    /**
     * @return list of projects for calendar.
     */
    List<Project> getProjectsToCalendar();
}
