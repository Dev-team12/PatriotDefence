package defencer.util.telegram.services;

import defencer.hibernate.HibernateQueryBuilder;
import defencer.hibernate.HibernateService;
import defencer.model.Project;
import defencer.service.factory.ServiceFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Nikita on 08.05.2017.
 */
public class ProjectTelegramService {


    /**
     * Creating message with data of all projects.
     */
    public static SendMessage getAllProjects() {

        List<Project> data = ServiceFactory.getProjectService().findByPeriod();
        List<List<InlineKeyboardButton>> allLines = new ArrayList<>();

        String result = "ProjectTelegramService:";

        for (Project temp : data) {
            List<InlineKeyboardButton> row = new LinkedList<>();

            InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
            keyboardButton.setText(temp.getDescription() + '(' + temp.getName() + ")");
            keyboardButton.setCallbackData("project:" + temp.getId());

            row.add(keyboardButton);
            allLines.add(row);
        }
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

        SendMessage message = new SendMessage();
        message.setReplyMarkup(keyboardMarkup.setKeyboard(allLines));
        message.setText("->Projects");

        return message;
    }


    /**
     * Creating inlineQuery with project by id.
     */
    public static SendMessage findProject(String id) {
        Project project = getProject(id);

        List<InlineKeyboardButton> row = new LinkedList<>();

        InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
        keyboardButton.setText("get data");
        keyboardButton.setCallbackData("DataProject:" + project.getId());
        row.add(keyboardButton);

        List<List<InlineKeyboardButton>> allLines = new ArrayList<>();
        allLines.add(row);

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        SendMessage message = new SendMessage();
        message.setReplyMarkup(keyboardMarkup.setKeyboard(allLines));
        message.setText("->Projects-->" + project.getName());

        return message;
    }


    /**
     * Getting full data of project by id.
     */
    public static SendMessage getData(String id) {

        Project project = getProject(id);

        String text = "->Projects-->" + project.getName() + "--->data\n";
        text += "Name:" + project.getNameId() + "\n";
        text += "Description:" + project.getDescription() + "\n";
        text += "Date start:" + project.getDateStart() + "\n";
        text += "Date finish:" + project.getDateFinish() + "\n";
        text += "Place:" + project.getPlace() + "\n";
        text += "Author:" + project.getAuthor() + "\n";
        text += "Instructors:" + project.getInstructors() + "\n";

        SendMessage message = new SendMessage();
        message.setText(text);
        return message;
    }


    /**
     * Getting project by id.
     */
    private static Project getProject(String id) {
        HibernateQueryBuilder hibernateQueryBuilder = new HibernateQueryBuilder(HibernateQueryBuilder.SELECT_QUERY, Project.class);
        hibernateQueryBuilder.with(HibernateQueryBuilder.ID_FIELD, id);

        return (Project) HibernateService.executeQuery(hibernateQueryBuilder).get(0);
    }
}
