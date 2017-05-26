package defencer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import defencer.data.CurrentUser;
import defencer.service.factory.ServiceFactory;
import defencer.util.NotificationUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import lombok.val;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Igor Hnes on 16.05.17.
 */
public class ChangePasswordController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton btnCancel;
    @FXML
    private JFXButton btnChange;
    @FXML
    private JFXPasswordField txtOldPassword;
    @FXML
    private JFXPasswordField txtNewPassword;
    @FXML
    private JFXPasswordField txtConfirmPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnCancel.setOnAction(e -> root.getScene().getWindow().hide());
        btnChange.setOnAction(e -> changePassword());
    }

    /**
     * Change password for current user.
     */
    private void changePassword() {
        val instructor = ServiceFactory.getInstructorService().findByEmail(CurrentUser.getLink().getEmail());
        if (!instructor.getPassword().equals(txtOldPassword.getText())) {
            NotificationUtil.warningAlert("Wrong", "old password isn't correct", NotificationUtil.SHORT);
            return;
        }
        if (!txtNewPassword.getText().equals(txtConfirmPassword.getText())) {
            NotificationUtil.warningAlert("Wrong", "passwords isn't match", NotificationUtil.SHORT);
            return;
        }
        ServiceFactory.getInstructorService().changePassword(CurrentUser.getLink().getId(), txtNewPassword.getText());
        root.getScene().getWindow().hide();
        NotificationUtil.warningAlert("Wrong", "your password has been changed", NotificationUtil.SHORT);
    }
}
