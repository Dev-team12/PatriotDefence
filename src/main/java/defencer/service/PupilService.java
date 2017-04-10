package defencer.service;

import defencer.model.Pupil;

/**
 * @author Igor Gnes on 4/6/17.
 */
public interface PupilService {

    /**
     * @param id its given project id.
     * @return {@link Pupil} by given project id.
     */
    Pupil findByProject(Long id);
}
