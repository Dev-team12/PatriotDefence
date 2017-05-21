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

    private static TelegramUtil telegramUtil = null;

    private TelegramBot telegramBot = null;

    private TelegramUtil() {
        telegramBot = new TelegramBot();
    }

    public static void main(String[] args) {
        new TelegramUtil().start();
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

    public void alertAboutProject(List<Instructor> instructorsList,Project project){

        for(Instructor instructor : instructorsList){

            SendMessage messageObject = new SendMessage()
                    .setText("You are using " + project.getNameId())
                    .setChatId(instructor.getTelegramID());

            try {
                telegramBot.sendMessage(messageObject);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
