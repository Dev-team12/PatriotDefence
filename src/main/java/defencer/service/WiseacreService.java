package defencer.service;

import java.util.List;
import java.util.Map;

/**
 * @author Igor Gnes on 4/17/17.
 */
public interface WiseacreService {

    /**
     * @return list of free car for project.
     */
    List<String> getFreeCar();

    /**
     * @return list of available project.
     */
    List<String> getAvailableProject();

    /**
     * @return list of free instructors.
     */
    List<String> getFreeInstructors();

    /**
     * @return map with project's name and times how often they were created.
     */
    Map<String, Long> getProjectStatistic();

    /**
     * @return map with instructor's name and work days for last months.
     */
    Map<String, Long> getInstructorStatistic();
}
