package defencer.service.impl.email;

import defencer.model.Instructor;
import defencer.model.Project;

/**
 * @author Igor Gnes on 5/4/17.
 */
public class RecoverBuilderImpl extends EmailBuilderImpl<Instructor> {

    private static final String RECOVERY_PASSWORD = "Recovery password";

    @Override
    String getMessageRecipient(Instructor instructor) {
        return instructor.getEmail();
    }

    @Override
    String getMessageSubject() {
        return RECOVERY_PASSWORD;
    }

    @Override
    String getMessageBody(Instructor instructor, Project project) {
        return "Dear "
                + instructor.getFirstName()
                + " "
                + instructor.getLastName()
                + "\n"
                + "You requested a password recovery to "
                + instructor.getEmail()
                + "\n"
                + "Your new password is: "
                + instructor.getPassword()
                + "\n"
                + "Have a nice day -)"
                + "\n"
                + "Your Patriot Defence!!!";
    }
}
