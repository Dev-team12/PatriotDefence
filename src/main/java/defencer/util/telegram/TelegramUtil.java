package defencer.util.telegram;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * @author Nikita on 08.05.2017.
 */
public class TelegramUtil {

    private TelegramBot telegramBot;

    private TelegramUtil() {
        telegramBot = new TelegramBot();
    }

    /**
     * Starting of util.
     */
    public void start() {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
