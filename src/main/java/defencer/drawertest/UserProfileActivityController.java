package defencer.drawertest;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Nikita on 14.04.2017.
 */
public class UserProfileActivityController implements Initializable {

    @FXML
    private ImageView userImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userImage.setOnDragOver(event -> {
            System.out.println("DROG OVER");
            event.acceptTransferModes(TransferMode.MOVE);
            event.consume();
        });

        userImage.setOnDragDropped(event -> {
            System.out.println("DROG DROPPED ");

            System.out.println(event.getDragboard().getFiles().get(0).getAbsolutePath());

            try {
                userImage.setImage(new Image(event.getDragboard().getFiles().get(0).toURI().toURL().toString()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            event.setDropCompleted(true);
        });
    }
}
