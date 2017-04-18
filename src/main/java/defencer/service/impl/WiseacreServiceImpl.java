package defencer.service.impl;

import defencer.dao.factory.DaoFactory;
import defencer.service.WiseacreService;

import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of {@link WiseacreService} interface.
 *
 * @author Igor Gnes on 4/17/17.
 */
public class WiseacreServiceImpl implements WiseacreService {

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<String> getFreeCar() {
        List<String> freeCars = new LinkedList<>();
        DaoFactory.getWiseacreDao().getFreeCar().forEach(s -> freeCars.add(s.getCarName()));
        return freeCars;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<String> getAvailableProject() {
        List<String> availableProject = new LinkedList<>();
        DaoFactory.getWiseacreDao().getAvailableProject().forEach(s -> availableProject.add(s.getName()));
        return availableProject;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<String> getFreeInstructors() {
        List<String> freeInstructors = new LinkedList<>();
        DaoFactory.getWiseacreDao().getFreeInstructors().forEach(s -> freeInstructors.add(s.getFirstName()));
        return freeInstructors;
    }
}
