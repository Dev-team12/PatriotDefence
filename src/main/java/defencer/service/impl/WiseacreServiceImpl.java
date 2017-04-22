package defencer.service.impl;

import defencer.dao.factory.DaoFactory;
import defencer.model.*;
import defencer.service.WiseacreService;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link WiseacreService} interface.
 *
 * @author Igor Gnes on 4/17/17.
 */
public class WiseacreServiceImpl extends CrudServiceImpl<AbstractEntity> implements  WiseacreService {

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
        DaoFactory.getWiseacreDao().getAvailableProject().forEach(s -> availableProject.add(s.getProjectName()));
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

    /**
     * {@inheritDoc}.
     */
    @Override
    public Map<String, Long> getProjectStatistic() {
        return DaoFactory.getWiseacreDao().getProjectStatistic();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Map<String, Long> getInstructorStatistic() {
        return DaoFactory.getWiseacreDao().getInstructorStatistic();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int getTotalInstructors() {
        return DaoFactory.getWiseacreDao().getTotalInstructors();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int getTotalApprentice() {
        return DaoFactory.getWiseacreDao().getTotalApprentice();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int getQuantityProjectForLastMonths() {
        return DaoFactory.getWiseacreDao().getQuantityProjectForLastMonths();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public int getQuantityApprenticeForLastMon() {
        return DaoFactory.getWiseacreDao().getQuantityApprenticeForLastMon();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Car> getCarForAdminDashboard() {
        return DaoFactory.getWiseacreDao().getCarForAdminDashboard();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<AvailableProject> getProjectForAdminDashboard() {
        return DaoFactory.getWiseacreDao().getProjectForAdminDashboard();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void createCar(Car car) throws SQLException {
        super.createEntity(car);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void deleteCar(Car car) throws SQLException {
        super.deleteEntity(car);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void createProject(AvailableProject project) throws SQLException {
        super.createEntity(project);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void deleteProject(AvailableProject project) throws SQLException {
        super.deleteEntity(project);
    }
}
