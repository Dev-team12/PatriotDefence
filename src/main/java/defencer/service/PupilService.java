package defencer.service;

import defencer.model.Apprentice;

/**
 * @author Igor Gnes on 4/6/17.
 */
public interface PupilService {

    /**
     * @param id its given project id.
     * @return {@link Apprentice} by given project id.
     */
    Apprentice findByProject(Long id);
}
