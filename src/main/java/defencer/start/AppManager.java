package defencer.start;

import defencer.util.HibernateUtil;
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
        Parent root = FXMLLoader.load(getClass().getResource("/waiting.fxml"));
        primaryStage.setTitle("Patriot Defence");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("css/main.css");
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            HibernateUtil.shutdown();
            System.out.println("shutdown hibernate");

            System.exit(1);
        });
    }
}
