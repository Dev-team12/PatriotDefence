package defencer.start;

import defencer.data.ControllersDataFactory;
import defencer.util.HibernateUtil;
import defencer.util.InternetConnectionCheckerUtil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * The main class in app.
 */
public class AppManager extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/drawerMain.fxml"));
        primaryStage.setTitle("Patriot Defence");
        primaryStage.getIcons().add(new Image("/image/PatriotDefence.png"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("css/main.css");
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            if (HibernateUtil.getSessionFactory().isOpen()) {
                HibernateUtil.shutdown();
            }
            Platform.exit();
        });

        Map<String, Object> data = new HashMap<>();
        data.put("stage", primaryStage);

        ControllersDataFactory.getLink().add(this.getClass(), data);

        InternetConnectionCheckerUtil internetConnectionCheckerUtil = new InternetConnectionCheckerUtil();
        internetConnectionCheckerUtil.start();
    }
}
