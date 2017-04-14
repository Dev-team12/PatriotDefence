package defencer.dao;

import defencer.model.Apprentice;

/**
 * @author Igor Gnes on 4/9/17.
 */
public interface ApprenticeDao {

    /**
     * @param id its given project id.
     * @return {@link Apprentice} by given project.
     */
    Apprentice findByProject(Long id);
}
