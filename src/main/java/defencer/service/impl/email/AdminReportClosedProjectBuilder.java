package defencer.service.impl.email;

import defencer.model.Instructor;
import defencer.model.Project;

/**
 * @author Igor Gnes on 5/6/17.
 */
public class AdminReportClosedProjectBuilder extends EmailBuilderImpl<Instructor> {

    private static final String PATRIOT_DEFENCE_CLOSED_PROJECT = "Patriot Defence. Closed project";

    @Override
    String getMessageRecipient(Instructor instructor) {
        return instructor.getEmail();
    }

    @Override
    String getMessageSubject() {
        return PATRIOT_DEFENCE_CLOSED_PROJECT;
    }

    @Override
    String getMessageBody(Instructor instructor, Project project) {
        return "Dear "
                + instructor.getFirstName()
                + " "
                + instructor.getLastName()
                + "\n"
                + "User "
                + project.getAuthor()
                + " closed a project."
                + "\n"
                + "Project: "
                + project.getNameId();
    }
}
