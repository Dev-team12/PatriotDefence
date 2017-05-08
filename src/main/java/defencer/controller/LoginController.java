package defencer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import defencer.data.CurrentUser;
import defencer.model.Instructor;
import defencer.service.factory.ServiceFactory;
import defencer.util.HibernateUtil;
import defencer.util.NotificationUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.SneakyThrows;
import lombok.val;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.BiPredicate;

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
    private JFXPasswordField txtUserPassword;
    @FXML
    private Hyperlink linkRecoverPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        txtUserEmail.setText("joyukr@ukr.net");
        txtUserPassword.setText("IzCQcnXxB6m1");
        btnLogin.setOnAction(e -> login());
        linkRecoverPassword.setOnAction(this::recoverPassword);
    }

    /**
     * Recover password.
     */
    @SneakyThrows
    private void recoverPassword(ActionEvent event) {

        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/RecoverPassword.fxml"));
        Parent parent = fxmlLoader.load();

        final Stage stage = new Stage();
        Scene value = new Scene(parent);
        value.getStylesheets().add("css/main.css");
        stage.setScene(value);
        stage.initModality(Modality.WINDOW_MODAL);
        Window window = ((Node) event.getSource()).getScene().getWindow();
        stage.initOwner(window);
        stage.show();
    }

    /**
     * Do login in application.
     */
    private void login() {

        final Instructor instructor = ServiceFactory.getInstructorService().findByEmail(txtUserEmail.getText());

        if (instructor == null || txtUserPassword == null) {
            NotificationUtil.warningAlert("Wrong", "user not found", NotificationUtil.SHORT);
            return;
        }

        BiPredicate<String, String> biPredicate = Object::equals;
        final boolean test = biPredicate.test(instructor.getPassword(), txtUserPassword.getText());
        if (!test) {
            NotificationUtil.warningAlert("Wrong", "password is wrong", NotificationUtil.SHORT);
            return;
        }

        val thread = new Thread(setCurrentUser());
        thread.start();

        rootLogin.getScene().getWindow().hide();
        authorization();
    }

    private Runnable setCurrentUser() {
        return () -> CurrentUser.newInstance(txtUserEmail.getText());
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

        stage.setOnCloseRequest(event -> {
            HibernateUtil.shutdown();
            Platform.exit();
        });
    }
}
