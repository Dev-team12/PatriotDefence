package defencer.service.impl;

import defencer.model.Instructor;
import defencer.model.Project;
import defencer.service.TelegramService;
import defencer.util.telegram.TelegramBot;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.List;

/**
 * Class for notification instructors about their's new projects.
 *
 * @author Igor Hnes on 29.05.17.
 *
 * @version 1.1
 */
public class TelegramServiceImpl implements TelegramService {

    @Override
    public void send(List<Instructor> instructors, Project project) {
        final TelegramBot bot = new TelegramBot();
        instructors.forEach(s -> {
            final SendMessage message = new SendMessage();
            if (s.getTelegramId() != null) {
                message
                        .setText(buildMessage(s, project))
                        .setChatId(s.getTelegramId());
            }
            try {
                bot.sendMessage(message);
            } catch (TelegramApiException e) {
                // NON
            }
        });
    }

    /**
     * Build message for given instructor.
     */
    private String buildMessage(Instructor instructor, Project project) {
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
                + "List of instructors: "
                + "\n"
                + project.getInstructors()
                + "\n"
                + project.getExpected()
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
