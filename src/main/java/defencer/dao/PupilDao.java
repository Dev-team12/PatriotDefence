package defencer.dao;

import defencer.model.Pupil;

/**
 * @author Igor Gnes on 4/9/17.
 */
public interface PupilDao {

    /**
     * @param id its given project id.
     * @return {@link Pupil} by given project.
     */
    Pupil findByProject(Long id);
}
