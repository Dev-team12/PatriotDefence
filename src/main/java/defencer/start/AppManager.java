package defencer.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main class in app.
 */
public class AppManager extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/test.fxml"));
        stage.setTitle("Patriot Defence");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
