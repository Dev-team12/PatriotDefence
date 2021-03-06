package defencer.controller.add;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import defencer.exception.entity.EntityAlreadyExistsException;
import defencer.model.Event;
import defencer.service.factory.ServiceFactory;
import defencer.util.NotificationUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author Nikita on 05.05.2017.
 */
public class NewEventController implements Initializable {

    @FXML
    private AnchorPane toor;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXButton btnAddEvent;
    @FXML
    private JFXButton btnCancel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnAddEvent.setOnMouseClicked(this::addEvent);
        btnCancel.setOnAction(e -> toor.getScene().getWindow().hide());
    }

    /**
     * Add new Event.
     */
    private void addEvent(MouseEvent mouseEvent) {
        if (name.getText() != null && name.getText().length() != 0
                && date.getValue() != null) {
            final Event event = new Event();
            event.setName(name.getText());
            event.setDate(date.getValue());
            create(event);
            toor.getScene().getWindow().hide();
        } else {
            NotificationUtil.errorAlert("Error", "Form isn't filled right.", NotificationUtil.LONG);
        }
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
