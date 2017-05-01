package defencer.dao;

import defencer.model.Project;

import java.util.List;

/**
 * @author Igor Gnes on 4/10/17.
 */
public interface ProjectDao extends CrudDao<Project, Long> {

    /**
     * @param defaultPeriod is 6 months.
     * @return list of entity for given period, default is six months.
     */
    List<Project> getProjectForGivenPeriod(Long defaultPeriod);

    /**
     * @param projectId for instructor and car.
     */
    void saveId(Long projectId);

    /**
     * @return list of project with parameters search.
     */
    List<Project> getFindProject(Long periodInDays, String projectName);
}
