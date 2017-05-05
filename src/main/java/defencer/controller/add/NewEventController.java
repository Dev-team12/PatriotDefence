package defencer.controller.add;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import defencer.exception.entity.EntityAlreadyExistsException;
import defencer.model.Event;
import defencer.service.factory.ServiceFactory;
import defencer.util.NotificationUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author Nikita on 05.05.2017.
 */
public class NewEventController implements Initializable {


    @FXML
    private JFXTextField name;
    @FXML
    private DatePicker date;
    @FXML
    private JFXButton acceptButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        acceptButton.setOnMouseClicked(this::addEvent);
    }


    /**
     * Add new Event.
     */
    private Event addEvent(MouseEvent mouseEvent) {
        if (name.getText() != null && name.getText().length() != 0
                && date.getValue() != null) {

            final Event event = new Event();
            event.setName(name.getText());
            event.setDate(date.getValue());

            create(event);
            name.getScene().getWindow().hide();

        } else {
            NotificationUtil.errorAlert("Error", "Form isn't filled right.", NotificationUtil.LONG);
        }

        return null;
    }


    /**
     * Create new Event.
     */
    private void create(Event event) {

        try {
            ServiceFactory.getEventService().createEntity(event);
        } catch (SQLException | EntityAlreadyExistsException e) {
            System.out.println(e.getMessage());
            NotificationUtil.warningAlert("Error", e.getMessage(), NotificationUtil.SHORT);
        }
    }


}
