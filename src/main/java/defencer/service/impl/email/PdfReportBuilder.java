package defencer.service.impl.email;

import defencer.data.CurrentUser;
import defencer.model.Project;

/**
 * @author Igor Gnes on 5/4/17.
 */
public class PdfReportBuilder extends EmailBuilderImpl<CurrentUser> {

    private static final String REPORT = "Report";

    @Override
    String getMessageRecipient(CurrentUser user) {
        return user.getEmail();
    }

    @Override
    String getMessageSubject() {
        return REPORT;
    }

    @Override
    String getMessageBody(CurrentUser user, Project project) {
        return "Dear "
                + user.getFirstName()
                + " "
                + user.getLastName()
                + "\n"
                + "Please download your doc report"
                + "\n"
                + "Have a nice day -)"
                + "\n"
                + "Your Patriot Defence!!!";
    }
}
