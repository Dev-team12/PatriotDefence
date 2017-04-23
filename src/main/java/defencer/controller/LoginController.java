package defencer.controller;

import com.jfoenix.controls.JFXButton;
import defencer.util.PreLoaderUtil;
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

/**
 * @author Igor Gnes on 4/15/17.
 */
public class LoginController implements Initializable {

    @FXML
    private AnchorPane rootLogin;
    @FXML
    private JFXButton btnLogin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (!PreLoaderUtil.getLink().isPreLoaded()) {
            PreLoaderUtil.getLink().start();
        }

        btnLogin.setOnAction(e -> {
            authorization();
        });
    }

    /**
     * Authorization, but not it's simple entrance.
     */
    @SneakyThrows
    private void authorization() {

        btnLogin.getScene().getWindow().hide();

        Parent root = FXMLLoader.load(getClass().getResource("/drawerMain.fxml"));

        final Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
        });

    }
}
