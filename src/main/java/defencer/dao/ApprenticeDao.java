package defencer.dao;

import defencer.model.Apprentice;

import java.util.List;

/**
 * @author Igor Gnes on 4/9/17.
 */
public interface ApprenticeDao {

    /**
     * @param id its given project id.
     * @return {@link Apprentice} by given project.
     */
    Apprentice findByProject(Long id);

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
}
