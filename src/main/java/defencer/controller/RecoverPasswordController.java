package defencer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import defencer.model.Instructor;
import defencer.service.EmailBuilder;
import defencer.service.factory.ServiceFactory;
import defencer.service.impl.email.RecoverBuilderImpl;
import defencer.util.NotificationUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.lang.RandomStringUtils;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author Igor Gnes on 5/4/17.
 */
public class RecoverPasswordController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXButton btnRecover;
    @FXML
    private JFXButton btnCancel;

    private static final int PASSWORD_LENGTH = 12;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnCancel.setOnAction(s -> root.getScene().getWindow().hide());

        btnRecover.setOnAction(e -> recover());
    }

    /**
     * Recover password by user's email.
     */
    private void recover() {
        if (txtEmail.getText() == null) {
            return;
        }

        final Instructor instructor = ServiceFactory.getInstructorService().findByEmail(txtEmail.getText());
        if (instructor == null) {
            NotificationUtil.warningAlert("Wrong", "user not found", NotificationUtil.SHORT);
            return;
        }

        final String newPassword = RandomStringUtils.randomAlphanumeric(PASSWORD_LENGTH);
        instructor.setPassword(newPassword);

        root.getScene().getWindow().hide();

        EmailBuilder<Instructor> emailBuilder = new RecoverBuilderImpl();
        ServiceFactory.getEmailService().sendMessage(emailBuilder.buildMessage(instructor));

        try {
            ServiceFactory.getInstructorService().updateEntity(instructor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        NotificationUtil.informationAlert("Success", "Dear " + instructor.getFirstName() + " please check ur email for new password", NotificationUtil.LONG);
    }
}
