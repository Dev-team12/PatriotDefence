package defencer.controller;

import defencer.util.PreLoaderUtil;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

import java.io.IOException;
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
                toDo();
                return null;
            }
        };

        new Thread(task).start();
        task.setOnSucceeded(event -> toNextLayout());
    }


    /**
     * task 4 new thread.
     */
    private void toDo() {
        while (true) {
            if (PreLoaderUtil.getPercents() >= 1.0) {
                return;
            } else {
                progressBar.setProgress(PreLoaderUtil.getPercents());
            }
        }
    }


    /**
     * jumping to the next layout.
     */
    private void toNextLayout() {
        progressBar.getScene().getWindow().hide();

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/drawerMain.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        final Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
