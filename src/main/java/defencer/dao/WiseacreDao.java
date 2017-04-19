package defencer.dao;

import defencer.model.AvailableProject;
import defencer.model.Car;
import defencer.model.Instructor;

import java.util.List;
import java.util.Map;

/**
 * @author Igor Gnes on 4/17/17.
 */
public interface WiseacreDao {

    /**
     * @return list of free {@link Car} for project.
     */
    List<Car> getFreeCar();

    /**
     * @return list of available {@link AvailableProject}.
     */
    List<AvailableProject> getAvailableProject();

    /**
     * @return list of free {@link Instructor} for project.
     */
    List<Instructor> getFreeInstructors();

    /**
     * @return map with project's name and times how often they were created for last months.
     */
    Map<String, Long> getProjectStatistic();

    /**
     * @return map with instructor's name and work days for last months.
     */
    Map<String, Long> getInstructorStatistic();
}
