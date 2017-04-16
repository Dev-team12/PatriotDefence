package defencer.service;

import defencer.model.Project;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Igor Gnes on 4/6/17.
 */
public interface ProjectService extends CrudService<Project, Long> {

    /**
     * @return list of project by period.
     */
    List<Project> findByPeriod(Long months);

    /**
     * @return list of projects for last months.
     */
    List<Project> getProjectsForLastMonths() throws SQLException;
}
