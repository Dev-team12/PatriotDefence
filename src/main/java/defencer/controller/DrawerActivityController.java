package defencer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import defencer.data.ControllersDataFactory;
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
import java.util.HashMap;
import java.util.Map;
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
    private ImageView logoutButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        apprenticeButton.setOnMouseClicked(click -> mouseClickButton(apprenticeButton));

        instructorButton.setOnMouseClicked(click -> mouseClickButton(instructorButton));

        profileButton.setOnMouseClicked(click -> mouseClickButton(profileButton));

        projectButton.setOnAction(click -> mouseClickButton(projectButton));

        logoutButton.setOnMouseClicked(click -> logout());
    }


    /**
     * It is using for handling click at drawer.
     */
    private void mouseClickButton(Button button) {

        Map<String,Object> data = new HashMap<>();

        switch (button.getId()) {

            case "profileButton":
                AnchorPane userPane = null;
                try {
                    userPane = FXMLLoader.load(getClass().getResource("/userProfile.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                changeStageTo(userPane, (AnchorPane) button.getScene().lookup("#currentLayout"));
                break;

            case "instructorButton":
                AnchorPane instructorPane = null;

                data.put("name","test1");
                ControllersDataFactory.getLink().add(UserProfileActivityController.class,data);



                ControllersDataFactory.getLink().get(UserProfileActivityController.class);

                try {
                    instructorPane = FXMLLoader.load(getClass().getResource("/entity/InstructorPage.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                changeStageTo(instructorPane, (AnchorPane) button.getScene().lookup("#currentLayout"));
                break;


            case "apprenticeButton":
                AnchorPane apprenticePane = null;

                data.put("name","test2");
                ControllersDataFactory.getLink().add(UserProfileActivityController.class,data);

                try {
                    apprenticePane = FXMLLoader.load(getClass().getResource("/entity/ApprenticePage.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                changeStageTo(apprenticePane, (AnchorPane) button.getScene().lookup("#currentLayout"));
                break;

            case "projectButton":

                AnchorPane projectPane = null;
                try {
                    projectPane = FXMLLoader.load(getClass().getResource("/entity/ProjectPage.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                changeStageTo(projectPane, (AnchorPane) button.getScene().lookup("#currentLayout"));
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

        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setTopAnchor(pane, 0.0);

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