package defencer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import defencer.data.ControllersDataFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.io.IOException;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Nikita on 13.04.2017.
 */
public class MainActivityController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private AnchorPane currentLayout;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXButton btbDashboard;
    @FXML
    private JFXButton btnLogout;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Map<String, Object> data = new HashMap<>();
        data.put("class", this);

        if (ControllersDataFactory.getLink().get(this.getClass()) == null) {
            ControllersDataFactory.getLink().add(this.getClass(), data);
        } else {
            ControllersDataFactory.getLink().update(this.getClass(), data);
        }

        try {
            Pane adminDashboard = FXMLLoader.load(getClass().getResource("/home/AdminDashboard.fxml"));
            changeStageTo(adminDashboard);

            VBox box = FXMLLoader.load(getClass().getResource("/drawer/drawer.fxml"));
            drawer.setSidePane(box);
        } catch (IOException e) {
            e.printStackTrace();
        }


        btbDashboard.setOnMouseClicked(click -> {
            try {
                Pane adminDashboard = FXMLLoader.load(getClass().getResource("/home/AdminDashboard.fxml"));
                changeStageTo(adminDashboard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {

            //transition.setRate(transition.getRate() *-1);
            // transition.play();

            if (drawer.isShown()) {
                closeDrawer();
            } else {
                openDrawer();
            }

        });


        btnLogout.setOnAction(e -> {
            logout();
            currentLayout.getScene().getWindow().hide();
        });
    }


    /**
     * Logout from system.
     */
    @SneakyThrows
    private void logout() {
        Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
        final Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /**
     * This method changes current stage to stage,that was given as input parameter.
     */
    private void changeStageTo(Pane pane) {

        if (currentLayout.getChildren().size() == 1) {
            currentLayout.getChildren().remove(0);
        }

        currentLayout.getChildren().add(pane);

        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
        AnchorPane.setTopAnchor(pane, 0.0);
    }


    /**
     * Closing drawer.
     */
    public void closeDrawer() {

        drawer.close();

        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(transition.getRate() * -1);
        transition.play();

        drawer.toBack();
    }


    /**
     * Opening drawer.
     */
    public void openDrawer() {

        drawer.toFront();
        drawer.open();

        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(transition.getRate() * -1);
        transition.play();
        //transition.setRate(1.0);
        //transition.play();
    }
}
