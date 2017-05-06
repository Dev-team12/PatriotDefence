package defencer.service.impl.email;

import defencer.model.Instructor;
import defencer.model.Project;

/**
 * @author Igor Gnes on 5/3/17.
 */
public class ConfirmBuilderImpl extends EmailBuilderImpl<Instructor> {

    private static final String REGISTRATION = "Confirm registration to Patriot Defence";

    @Override
    String getMessageRecipient(Instructor instructor) {
        return instructor.getEmail();
    }

    @Override
    String getMessageSubject() {
        return REGISTRATION;
    }

    @Override
    String getMessageBody(Instructor instructor, Project project) {
        return "Dear "
                + instructor.getFirstName()
                + " "
                + instructor.getLastName()
                + "\n"
                + "You were registered in Patriot Defence "
                + "\n"
                + "For login user your email: "
                + instructor.getEmail()
                + "\n"
                + "and password: "
                + instructor.getPassword()
                + "\n"
                + "Have a nice day -)"
                + "\n"
                + "Your Patriot Defence!!!";
    }
}
