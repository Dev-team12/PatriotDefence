package defencer.controller;

import defencer.util.PreLoaderUtil;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ProgressIndicator;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Nikita on 19.04.2017.
 */
public class LoadingController implements Initializable {

    @FXML
    private ProgressIndicator progressBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                PreLoaderUtil.getLink().start();
                toDo();
                return null;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(event -> toNextLayout());
    }


    /**
     * Task 4 new thread.
     */
    private void toDo() {
        while (true) {
            if (PreLoaderUtil.getPercents() >= 1.0) {
                return;
            }
            try {
                final int millis = 500;
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Jumping to the next layout.
     */
    @SneakyThrows
    private void toNextLayout() {
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        progressBar.getScene().setRoot(root);
    }
}
