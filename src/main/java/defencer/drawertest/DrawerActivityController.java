package defencer.drawertest;

import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Nikita on 13.04.2017.
 */
public class DrawerActivityController implements Initializable {

    @FXML
    private ImageView logoutButton;

    @FXML
    private Button pupilButton;

    @FXML
    private Button instructorButton;

    @FXML
    private Button profileButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pupilButton.setOnMouseClicked(click -> {
            mouseClickButton(pupilButton);
        });

        instructorButton.setOnMouseClicked(click -> {
            mouseClickButton(instructorButton);
        });

        profileButton.setOnMouseClicked(click -> {
            mouseClickButton(profileButton);
        });

        logoutButton.setOnMouseClicked(click -> {
            logout();
        });

    }


    /**
     * It is using for handling click at drawer.
     */
    private void mouseClickButton(Button button) {

        switch (button.getId()) {

            case "profileButton":
                AnchorPane userPane = null;
                try {
                    userPane = FXMLLoader.load(getClass().getResource("/entity/userProfile.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                changeStageTo(userPane, (AnchorPane) button.getScene().lookup("#currentLayout"));

               // NotificationUtil.errornAlert("Drawer","u clicked profileButton",10.0);
                break;


            case "instructorButton":
                AnchorPane instructorPane = null;
                try {
                    instructorPane = FXMLLoader.load(getClass().getResource("/entity/InstructorPage.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                changeStageTo(instructorPane, (AnchorPane) button.getScene().lookup("#currentLayout"));

                //NotificationUtil.informationAlert("Drawer","u clicked instructorButton",10.0);
                break;


            case "pupilButton":
                AnchorPane pupilPane = null;
                try {
                    pupilPane = FXMLLoader.load(getClass().getResource("/entity/PupilPage.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                changeStageTo(pupilPane, (AnchorPane) button.getScene().lookup("#currentLayout"));

               // NotificationUtil.warningAlert("Drawer","u clicked pupilButton",10.0);
                break;


            default:
                ((JFXDrawer) button.getScene().lookup("#drawer")).close();
                break;
        }
    }


    /**
     * This method changes current stage.
     */
    private void changeStageTo(Pane pane, AnchorPane anchorPane) {

        if (anchorPane.getChildren().size() == 1) {
            anchorPane.getChildren().remove(0);
        }

        anchorPane.getChildren().add(pane);

        anchorPane.setRightAnchor(pane, 0.0);
        anchorPane.setLeftAnchor(pane, 0.0);
        anchorPane.setBottomAnchor(pane, 0.0);
        anchorPane.setTopAnchor(pane, 0.0);

        ((JFXDrawer) pane.getScene().lookup("#drawer")).close();
    }


    /**
     * This method changes stage root element to logout fxml.
     */
    private void logout() {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
            logoutButton.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}