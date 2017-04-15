package defencer.util;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * @author Nikita on 14.04.2017.
 */
public class NotificationUtil{

    private static final Pos DEFAULT_POSITION = Pos.TOP_RIGHT;
    public static final Double SHORT = 3.0;
    public static final Double LONG = 5.0;

    /**
     * Creating information notification.
     */
    public static void informationAlert(String title, String message, Double duration) {

        Notifications notifications = Notifications.create()
                .title(title)
                .text(message)
                .graphic(null)
                .hideAfter(Duration.seconds(duration))
                .position(DEFAULT_POSITION);
        notifications.showInformation();
    }

    /**
     * Creating error notification.
     */
    public static void errornAlert(String title, String message, Double duration) {

        Notifications notifications = Notifications.create()
                .title(title)
                .text(message)
                .graphic(null)
                .hideAfter(Duration.seconds(duration))
                .position(DEFAULT_POSITION);
        notifications.showError();
    }

    /**
     * Creating warning notification.
     */
    public static void warningAlert(String title, String message, Double duration) {

        Notifications notifications = Notifications.create()
                .title(title)
                .text(message)
                .graphic(null)
                .hideAfter(Duration.seconds(duration))
                .position(DEFAULT_POSITION);
        notifications.showWarning();
    }
}