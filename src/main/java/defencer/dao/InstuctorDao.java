package defencer.dao;

import defencer.model.Instructor;

/**
 * @author Igor Gnes on 3/30/17.
 */
public interface InstuctorDao extends GrudDag<Instructor, Long> {

    /**
     * Fetches {@link Instructor} entity by provided email.
     * 
     * @param email instructor's email address, must not be {@literal null}.
     * @return {@link Instructor} entity associated with provided email, or {@literal null} if none found.
     */
    Instructor findByEmail(String email);

    /**
     * Fetches {@link Instructor} entity by provided phone.
     *
     * @param phone instructor's phone number, must not be {@literal null}.
     * @return {@link Instructor} entity associated with provided phone, or {@literal null} if none found.
     */
    Instructor findByPhone(String phone);
}
