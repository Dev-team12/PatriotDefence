package defencer.service;

import defencer.model.Instructor;
import defencer.model.Project;

/**
 * @author Igor Hnes on 21.05.17.
 */
public interface SmsService {

    /**
     * Send Message by SmsService.
     */
    void send(String phone, Instructor instructor, Project project);

    /**
     * @return balance on ur sms service.
     */
    String getBalance();
}
