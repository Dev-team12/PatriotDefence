package defencer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import defencer.data.CurrentUser;
import defencer.util.NotificationUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.ResourceBundle;
import javax.persistence.NoResultException;

/**
 * @author Igor Gnes on 4/15/17.
 */
public class LoginController implements Initializable {

    @FXML
    private AnchorPane rootLogin;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private JFXTextField txtUserEmail;
    @FXML
    private JFXTextField txtUserPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        txtUserEmail.setText("joyukr@ukr.net");
        btnLogin.setOnAction(e -> login());
    }

    /**
     * Do login in application.
     */
    private void login() {
        try {
            CurrentUser.newInstance(txtUserEmail.getText());
        } catch (NoResultException e) {
            NotificationUtil.warningAlert("Wrong", "user not found", NotificationUtil.SHORT);
            return;
        }
        rootLogin.getScene().getWindow().hide();
        authorization();
    }

    /**
     * Authorization, but not it's simple entrance.
     */
    @SneakyThrows
    private void authorization() {
        Parent root = FXMLLoader.load(getClass().getResource("/drawerMain.fxml"));
        final Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("css/main.css");
        stage.setScene(scene);
        stage.show();
    }
}
