package defencer.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Igor Gnes on 4/23/17.
 */
public class IsNoInternetConnectionController implements Initializable {

    @FXML
    private JFXButton btnTryNow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnTryNow.setOnAction(e -> tryNow());
    }

    /**
     * Try to reload app if internet connection is right.
     */
    @SneakyThrows
    private void tryNow() {
        final Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/drawerMain.fxml"));
        primaryStage.setTitle("Patriot Defence");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("css/main.css");
        primaryStage.show();
    }
}
