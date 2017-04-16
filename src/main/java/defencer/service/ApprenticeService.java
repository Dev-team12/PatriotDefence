package defencer.service;

import defencer.model.Apprentice;

import java.util.List;

/**
 * @author Igor Gnes on 4/6/17.
 */
public interface ApprenticeService extends CrudService<Apprentice, Long> {

    /**
     * @param id its given project id.
     * @return {@link Apprentice} by given project id.
     */
    Apprentice findByProject(Long id);

    List<Apprentice> getApprentice();
}
