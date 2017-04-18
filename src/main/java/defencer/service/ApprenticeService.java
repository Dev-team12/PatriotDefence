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

    /**
     * @return list of {@link Apprentice} for last months.
     */
    List<Apprentice> getApprenticeLastMonths();

    /**
     * @param email instructor's email address.
     *
     * @return instructor with given email.
     */
    Apprentice findByEmail(String email);
}
