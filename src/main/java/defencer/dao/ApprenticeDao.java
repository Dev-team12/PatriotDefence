package defencer.dao;

import defencer.model.Apprentice;

import java.util.List;

/**
 * @author Igor Gnes on 4/9/17.
 */
public interface ApprenticeDao {

    /**
     * @return list of project for given period.
     */
    List<Apprentice> findByPeriod(Long period, String projectName);

    /**
     * @return list of {@link Apprentice} for last months.
     */
    List<Apprentice> getApprentice();

    /**
     * Fetches {@link Apprentice} entity by provided email.
     *
     * @param email apprentice's email address, must not be {@literal null}.
     * @return {@link Apprentice} entity associated with provided email, or {@literal null} if none found.
     */
    Apprentice findByEmail(String email);

    /**
     * Delete apprentice with given id.
     *
     * @param id is given apprentice's id
     */
    void deleteApprenticeById(Long id);
}
