package defencer.service.impl.email;

import defencer.model.Instructor;
import defencer.model.Project;

/**
 * @author Igor Gnes on 5/6/17.
 */
public class AdminReportCreatedProjectBuilder extends EmailBuilderImpl<Instructor> {

    private static final String PATRIOT_DEFENCE_CREATED_A_NEW_PROJECT = "Patriot Defence. Created a new project";

    @Override
    String getMessageRecipient(Instructor instructor) {
        return instructor.getEmail();
    }

    @Override
    String getMessageSubject() {
        return PATRIOT_DEFENCE_CREATED_A_NEW_PROJECT;
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
                + " created a new project."
                + "\n"
                + "Project: "
                + project.getNameId()
                + "\n"
                + "Start Date: "
                + project.getDateStart()
                + " Finish Date: "
                + project.getDateFinish()
                + "\n"
                + "Place: "
                + project.getPlace()
                + "\n"
                + "Description: "
                + project.getDescription()
                + "\n"
                + "Have a nice day -)"
                + "\n"
                + "Your Patriot Defence!!!";
    }
}
