package defencer.service.impl.email;

import defencer.model.Instructor;
import defencer.model.Project;

/**
 * @author Igor Gnes on 5/4/17.
 */
public class InviteProjectBuilderImpl extends EmailBuilderImpl<Instructor> {

    private static final String PROJECT_PATRIOT_DEFENCE = "Project Patriot Defence";

    @Override
    String getMessageRecipient(Instructor instructor) {
        return instructor.getEmail();
    }

    @Override
    String getMessageSubject() {
        return PROJECT_PATRIOT_DEFENCE;
    }

    @Override
    String getMessageBody(Instructor instructor, Project project) {

        return "Dear "
                + instructor.getFirstName()
                + " "
                + instructor.getLastName()
                + " you were invited on course from Patriot Defence."
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
                + "Full list of instructors: "
                + "\n"
                + project.getInstructors()
                + "\n"
                + "Author of project "
                + project.getAuthor()
                + "\n"
                + "Please confirm participation."
                + "\n"
                + "Have a nice day -)"
                + "\n"
                + "Your Patriot Defence!!!";
    }
}
