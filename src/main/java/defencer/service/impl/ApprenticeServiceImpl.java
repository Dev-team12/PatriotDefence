package defencer.service.impl;

import defencer.dao.factory.DaoFactory;
import defencer.exception.entity.EntityAlreadyExistsException;
import defencer.model.Apprentice;
import defencer.service.ApprenticeService;
import lombok.val;

import java.sql.SQLException;
import java.util.List;

/**
 * Basic implementation of {@link ApprenticeService} interface.
 *
 * @author igor on 06.12.16.
 */
public class ApprenticeServiceImpl extends CrudServiceImpl<Apprentice> implements ApprenticeService {

    /**
     * {@inheritDoc}.
     */
    @Override
    public Apprentice createEntity(Apprentice apprentice) throws SQLException {
        if (!this.emailAvailable(apprentice)) {
            throw new EntityAlreadyExistsException("Supplied email is already taken: " + apprentice.getEmail());
        }
        return super.createEntity(apprentice);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Apprentice> findByPeriod(Long period, String projectName) {
        return DaoFactory.getApprenticeDao().findByPeriod(period, projectName);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Apprentice> getApprenticeLastMonths() {
        return DaoFactory.getApprenticeDao().getApprentice();
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Apprentice findByEmail(String email) {
        return DaoFactory.getApprenticeDao().findByEmail(email);
    }


    /**
     * Checks if supplied email is already in the database.
     *
     * @param apprentice to check email for.
     * @return true if email available, false otherwise.
     */
    private boolean emailAvailable(Apprentice apprentice) {
        val email = apprentice.getEmail();
        return this.findByEmail(email) == null;
    }
}
