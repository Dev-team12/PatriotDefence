package defencer.dao;

import defencer.model.AvailableProject;
import defencer.model.Car;
import defencer.model.Instructor;

import java.util.List;

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
}
