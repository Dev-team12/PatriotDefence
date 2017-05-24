package defencer.util.telegram;

import defencer.model.Instructor;
import defencer.model.Project;
import defencer.util.HibernateUtil;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.List;

/**
 * @author Nikita on 08.05.2017.
 */
public class TelegramUtil {

    private static TelegramUtil telegramUtil;

    private TelegramBot telegramBot;

    private TelegramUtil() {
        telegramBot = new TelegramBot();
    }

    /**
     * Starting of util.
     */
    public void start() {
        ApiContextInitializer.init();

        HibernateUtil.getSessionFactory();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get instance of util.
     */
    public static TelegramUtil getLink() {
        if (telegramUtil == null) {
            telegramUtil = new TelegramUtil();
        }

        return telegramUtil;
    }

    /**
     * Alerting all members about project.
     */
    public void alertAboutProject(List<Instructor> instructors, Project project) {

        for (Instructor instructor : instructors) {
            SendMessage messageObject = new SendMessage();
            if (instructor.getTelegramId() != null) {
                messageObject
                        .setText(buildMessage(instructor, project))
                        .setChatId(instructor.getTelegramId());
            }
            try {
                telegramBot.sendMessage(messageObject);
            } catch (TelegramApiException e) {
                // NON
            }
        }
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
