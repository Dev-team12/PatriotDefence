package defencer.util.telegram;

import defencer.util.telegram.services.ProjectTelegramService;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * @author Nikita on 08.05.2017.
 */
class TelegramBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {

        if (update.getMessage() != null) {
            messageHandler(update);
        } else {
            inlineQueryHandler(update);
        }

    }

    /**
     * Handling of basic messages.
     */
    private void messageHandler(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            SendMessage message = null;

            String gotMessage = update.getMessage().getText();

            if (gotMessage.contains("@")) {
                gotMessage = gotMessage.substring(0, gotMessage.indexOf("@"));
            }

            switch (gotMessage) {
                case "/projects":
                    message = ProjectTelegramService.getAllProjects();
                    message.setChatId(update.getMessage().getChatId());
                    break;

                default:
                    break;
            }

            try {
                sendMessage(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Handing of inline query(button).
     */
    private void inlineQueryHandler(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String data = callbackQuery.getData();

        System.out.println("data:" + data);

        String type = data.substring(0, data.indexOf(":"));
        String id = data.substring(data.indexOf(":") + 1, data.length());

        SendMessage message = null;

        System.out.println(type + ":" + id);

        switch (type) {
            case "project":
                message = ProjectTelegramService.findProject(id);
                break;

            case "DataProject":
                message = ProjectTelegramService.getData(id);
                break;

            default:
                break;
        }

        message.setChatId(update.getCallbackQuery().getMessage().getChatId());
        try {
            sendMessage(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "PatriotDefencer_bot";
    }

    @Override
    public String getBotToken() {
        return "391523749:AAHukg7XkEAdVMyfQt-fTUaUANVMAIhEwUE";
    }

}