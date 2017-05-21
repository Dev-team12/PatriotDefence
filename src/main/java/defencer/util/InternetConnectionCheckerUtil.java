package defencer.util;

import defencer.data.ControllersDataFactory;
import defencer.start.AppManager;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Nikita on 07.05.2017.
 */
public class InternetConnectionCheckerUtil {

    private static URL urlForCheck;

    private static final Long WAITING = 5000L;
    private static final int REACHABLE_WAITING = 1000;

    private static Task<Void> noInternetTask;


    public InternetConnectionCheckerUtil() {
        try {
            urlForCheck = new URL("http://www.google.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        noInternetTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (true) {
                    if (!checkConnection()) {
                        break;
                    }

                    try {
                        Thread.sleep(WAITING);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                return null;
            }
        };

        noInternetTask.setOnSucceeded(event -> {
            Stage stage = (Stage) ControllersDataFactory.getLink().get(AppManager.class, "stage");

            System.out.println(stage);

            try {
                Parent root = FXMLLoader.load(getClass().getResource("/IsNoInternetConnection.fxml"));
                Scene scene = new Scene(root);
                stage.hide();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.println(e.toString());

                e.printStackTrace();
            }
            HibernateUtil.shutdown();

            NotificationUtil.errorAlert("Error", "No internet connection.", NotificationUtil.SHORT);
        });
    }


    /**
     * Starting of util.
     */
    public void start() {
        new Thread(noInternetTask).start();
    }


    /**
     * Checking of existing internet connection.
     */
    public static boolean checkConnection() {
        InetAddress in = null;

        try {
            in = InetAddress.getByName(urlForCheck.getHost());

            return in.isReachable(REACHABLE_WAITING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new NullPointerException();
    }

}
