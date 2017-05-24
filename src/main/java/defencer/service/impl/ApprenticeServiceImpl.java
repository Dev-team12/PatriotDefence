package defencer.service.impl;

import defencer.dao.factory.DaoFactory;
import defencer.exception.entity.EntityAlreadyExistsException;
import defencer.model.Apprentice;
import defencer.service.ApprenticeService;
import defencer.service.CryptoService;
import defencer.service.cryptography.CryptoApprentice;
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
        CryptoService<Apprentice> cryptoService = new CryptoApprentice();
        final Apprentice encryptApprentice = cryptoService.encryptEntity(apprentice);
        return super.createEntity(encryptApprentice);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Apprentice> findByPeriod(Long period, String projectName) {
        val encryptApprentice = DaoFactory.getApprenticeDao().findByPeriod(period, projectName);
        CryptoService<Apprentice> cryptoService = new CryptoApprentice();
        return cryptoService.decryptEntityList(encryptApprentice);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public List<Apprentice> getApprenticeLastMonths() {
        final List<Apprentice> encryptApprentice = DaoFactory.getApprenticeDao().getApprentice();
        CryptoService<Apprentice> cryptoService = new CryptoApprentice();
        return cryptoService.decryptEntityList(encryptApprentice);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public Apprentice findByEmail(String email) {
        CryptoService<Apprentice> cryptoService = new CryptoApprentice();
        final Apprentice apprentice = DaoFactory.getApprenticeDao().findByEmail(email);
        return cryptoService.decryptEntity(apprentice);
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public void deleteApprenticeById(Long id) {
        DaoFactory.getApprenticeDao().deleteApprenticeById(id);
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
