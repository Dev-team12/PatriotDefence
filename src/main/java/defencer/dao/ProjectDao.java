package defencer.dao;

import defencer.model.Project;

import java.util.List;

/**
 * @author Igor Gnes on 4/10/17.
 */
public interface ProjectDao extends CrudDao<Project, Long> {

    /**
     * @return list of entity for last months.
     */
    List<Project> getProjectForGivenPeriod();

    /**
     * @param projectId for instructor and car.
     */
    void saveId(Long projectId);
}
