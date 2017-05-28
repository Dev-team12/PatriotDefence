package defencer.controller;

import com.jfoenix.controls.JFXButton;
import defencer.data.ControllersDataFactory;
import defencer.start.AppManager;
import defencer.util.InternetConnectionCheckerUtil;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Igor Gnes on 4/23/17.
 */
public class IsNoInternetConnectionController implements Initializable {

    @FXML
    private JFXButton btnTryNow;

    private Thread checkingThread;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnTryNow.setOnAction(e -> tryNow());
        Task<Void> checkingTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    if (InternetConnectionCheckerUtil.checkConnection()) {
                        break;
                    } else {
                        Thread.sleep(InternetConnectionCheckerUtil.WAITING);
                    }
                }
                return null;
            }
        };

        checkingTask.setOnSucceeded(event -> {
            Stage stage = (Stage) ControllersDataFactory.getLink().get(AppManager.class, "stage");

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/waiting.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root);
            stage.hide();
            stage.setScene(scene);
            scene.getStylesheets().add("css/main.css");
            stage.show();

            InternetConnectionCheckerUtil internetConnectionCheckerUtil = new InternetConnectionCheckerUtil();
            internetConnectionCheckerUtil.start();
        });

        checkingThread = new Thread(checkingTask);
        checkingThread.start();
    }

    /**
     * Try to reload app if internet connection is right.
     */
    @SneakyThrows
    private void tryNow() {
        if (InternetConnectionCheckerUtil.checkConnection()) {

            checkingThread.interrupt();

            Stage stage = (Stage) ControllersDataFactory.getLink().get(AppManager.class, "stage");

            Parent root = FXMLLoader.load(getClass().getResource("/waiting.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);

            InternetConnectionCheckerUtil internetConnectionCheckerUtil = new InternetConnectionCheckerUtil();
            internetConnectionCheckerUtil.start();
        }
    }
}
