package defencer.controller;

import com.jfoenix.controls.JFXButton;
import defencer.data.ControllersDataFactory;
import defencer.data.CurrentUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/**
 * @author Nikita on 13.04.2017.
 */
public class DrawerActivityController implements Initializable {

    @FXML
    private JFXButton profileButton;
    @FXML
    private JFXButton instructorButton;
    @FXML
    private JFXButton apprenticeButton;
    @FXML
    private JFXButton projectButton;
    @FXML
    private JFXButton calendarButton;
    @FXML
    private ImageView logoutButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        apprenticeButton.setOnMouseClicked(click -> mouseClickButton(apprenticeButton));

        instructorButton.setOnMouseClicked(click -> mouseClickButton(instructorButton));

        profileButton.setOnMouseClicked(click -> mouseClickButton(profileButton));

        projectButton.setOnAction(click -> mouseClickButton(projectButton));

        calendarButton.setOnMouseClicked(event -> mouseClickButton(calendarButton));

        logoutButton.setOnMouseClicked(click -> logout());
    }


    /**
     * It is using for handling click at drawer.
     */
    private void mouseClickButton(Button button) {

        AnchorPane newPane = null;

        switch (button.getId()) {


            case "profileButton":
                try {
                    newPane = FXMLLoader.load(getClass().getResource("/userProfile.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                changeStageTo(newPane, (AnchorPane) button.getScene().lookup("#currentLayout"));
                break;


            case "instructorButton":
                try {
                    newPane = FXMLLoader.load(getClass().getResource("/entity/InstructorPage.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                changeStageTo(newPane, (AnchorPane) button.getScene().lookup("#currentLayout"));
                break;


            case "apprenticeButton":
                try {
                    newPane = FXMLLoader.load(getClass().getResource("/entity/ApprenticePage.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                changeStageTo(newPane, (AnchorPane) button.getScene().lookup("#currentLayout"));
                break;


            case "projectButton":
                try {
                    newPane = FXMLLoader.load(getClass().getResource("/entity/ProjectPage.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                changeStageTo(newPane, (AnchorPane) button.getScene().lookup("#currentLayout"));
                break;


            case "calendarButton":
                try {
                    newPane = FXMLLoader.load(getClass().getResource("/Calendar.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                changeStageTo(newPane, (AnchorPane) button.getScene().lookup("#currentLayout"));
                break;

            default:
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

        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setTopAnchor(pane, 0.0);

        MainActivityController mainActivityController = (MainActivityController) ((HashMap) ControllersDataFactory.getLink().get(MainActivityController.class)).get("class");
        mainActivityController.closeDrawer();
    }

    /**
     * This method changes stage root element to logout fxml.
     */
    @SneakyThrows
    private void logout() {
        CurrentUser.out();
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        logoutButton.getScene().setRoot(root);
    }
}