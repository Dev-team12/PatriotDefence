package defencer.service;

import java.util.List;

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
}
