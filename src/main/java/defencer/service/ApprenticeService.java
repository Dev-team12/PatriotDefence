package defencer.service;

import defencer.model.Apprentice;

import java.util.List;

/**
 * @author Igor Gnes on 4/6/17.
 */
public interface ApprenticeService extends CrudService<Apprentice, Long> {

    /**
     * @return list of apprentice for given period and project name.
     */
    List<Apprentice> findByPeriod(Long period, String projectName);

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

    /**
     * Delete apprentice with given id.
     *
     * @param id is given apprentice's id
     */
    void deleteApprenticeById(Long id);
}
