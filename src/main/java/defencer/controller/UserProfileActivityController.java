package defencer.controller;

import defencer.data.CurrentUser;
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
    private ImageView showPassword;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label password;

    private boolean showPasswordVar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        showPasswordVar = false;

        factoryInitialization();

        dragAndDropInitialization();

        //showPassword.setOnMouseClicked(event -> {
           // if(showPassword.getImage().)
       // });
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

        /*if (currentUser.getStatus()) {
            status.setText("online");
        } else {
            status.setText("offline");
        }*/
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
