package defencer.controller;

import com.jfoenix.controls.JFXButton;
import defencer.data.CurrentUser;
import defencer.service.factory.ServiceFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @author Nikita on 14.04.2017.
 */
public class UserProfileActivityController implements Initializable {

    @FXML
    private AnchorPane dragAndDropArea;
    @FXML
    private ImageView userImage;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label status;
    @FXML
    private Label projectName;
    @FXML
    private JFXButton btnYes;
    @FXML
    private JFXButton btnNo;

    @FXML
    private Label dateStart;
    @FXML
    private Label dateFinish;
    @FXML
    private Label place;
    @FXML
    private Label author;
    @FXML
    private Label description;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        factoryInitialization();

        dragAndDropInitialization();

        btnYes.setOnAction(e -> agree());

        btnNo.setOnAction(e -> disagree());
    }

    /**
     * Set status free for instructor if he cat't take the project.
     */
    private void disagree() {
        final CurrentUser currentUser = CurrentUser.getLink();
        try {
            ServiceFactory.getWiseacreService().updateCurrentUser(currentUser.getId(), "FREE");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        CurrentUser.refresh(CurrentUser.getLink().getEmail());
        factoryInitialization();
    }

    /**
     * Set status busy if instructor take the project.
     */
    private void agree() {
        final CurrentUser currentUser = CurrentUser.getLink();
        try {
            ServiceFactory.getWiseacreService().updateCurrentUser(currentUser.getId(), "BUSY");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        CurrentUser.refresh(CurrentUser.getLink().getEmail());
        factoryInitialization();
    }

    /**
     * Setting data of current user.
     */
    private void factoryInitialization() {
        CurrentUser currentUser = CurrentUser.getLink();

        firstName.setText(currentUser.getFirstName());
        lastName.setText(currentUser.getLastName());
        phone.setText(currentUser.getPhoneNumber());
        email.setText(currentUser.getEmail());
        status.setText(currentUser.getStatus());

        initializeProject(currentUser);
    }

    /**
     * @param currentUser for initialize project.
     */
    private void initializeProject(CurrentUser currentUser) {
        projectName.setText("Confirm " + currentUser.getProjectName() + " project");
        dateStart.setText("Date start: " + currentUser.getProjectDateStart().toString());
        dateFinish.setText("Date finish: " + currentUser.getProjectDateFinish().toString());
        place.setText("Place: " + currentUser.getProjectPlace());
        author.setText("Author: " + currentUser.getProjectAuthor());
        description.setText(currentUser.getProjectDescription());
    }

    /**
     * Initialization of dragAndDrop.
     */
    private void dragAndDropInitialization() {

        dragAndDropArea.getChildren().forEach(s -> s.setVisible(true));

        dragAndDropArea.setOnMouseClicked(event -> {
            System.out.println(event.getClickCount());
            if (event.getClickCount() >= 2) {
                System.out.println("DOUBLE CLICK");
            }
        });

        dragAndDropArea.setOnDragOver(event -> {
            if (event.getDragboard().getFiles().size() == 1) {
                String name = event.getDragboard().getFiles().get(0).getName();
                name = name.substring(name.indexOf(".") + 1, name.length());

                if (name.equals("jpeg") || name.equals("jpg") || name.equals("png")) {
                    event.acceptTransferModes(TransferMode.MOVE);
                } else {
                    event.acceptTransferModes(TransferMode.NONE);
                }
            } else {
                event.acceptTransferModes(TransferMode.NONE);
            }
            event.consume();
        });

        dragAndDropArea.setOnDragEntered(event -> {
            for (Node temp : dragAndDropArea.getChildren()) {
                temp.setVisible(true);
            }
        });

        dragAndDropArea.setOnDragExited(event -> {
            for (Node temp : dragAndDropArea.getChildren()) {
                temp.setVisible(false);
            }
        });

        dragAndDropArea.setOnDragDropped(event -> {
            try {
                userImage.setImage(new Image(event.getDragboard().getFiles().get(0).toURI().toURL().toString()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            event.setDropCompleted(true);
        });
    }
}
