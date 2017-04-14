package defencer.drawertest;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

import java.net.URL;
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
    private ImageView home;
    @FXML
    private JFXButton btbDashboard;
    @FXML
    private JFXButton btnLogout;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Pane adminDashboard = FXMLLoader.load(getClass().getResource("/home/AdminDashboard.fxml"));
            changeStageTo(adminDashboard);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
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
            transition.setRate(transition.getRate() * -1);
            transition.play();

            if (drawer.isShown()) {
                drawer.close();
            } else {
                drawer.open();
            }
        });

        btnLogout.setOnAction(e -> {
            Pane adminDashboard = null;
            try {
                adminDashboard = FXMLLoader.load(getClass().getResource("/login.fxml"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            changeStageTo(adminDashboard);
        });
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
}
