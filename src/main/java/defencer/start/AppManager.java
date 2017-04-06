package defencer.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The main class in app.
 */
public class AppManager extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Patriot Defence");
        primaryStage.centerOnScreen();
//        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(new Image("/image/logo2.png"));
        Parent root = FXMLLoader.load(getClass().getResource("/welcome.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(0, "/css/main.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
