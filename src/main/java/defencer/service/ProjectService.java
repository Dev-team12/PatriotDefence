package defencer.service;

import defencer.model.Project;

import java.util.List;

/**
 * @author Igor Gnes on 4/6/17.
 */
public interface ProjectService {

    /**
     * @return list of project by period.
     */
    List<Project> findByPeriod(); // todo add parameters.

}
