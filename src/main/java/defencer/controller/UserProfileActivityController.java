package defencer.controller;

import defencer.data.ControllersDataFactory;
import defencer.data.CurrentUser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Nikita on 14.04.2017.
 */
public class UserProfileActivityController implements Initializable {

    @FXML
    private ImageView userImage;

    @FXML
    private Label nameLabel;

    @FXML
    private Label phoneNumber;

    @FXML
    private Label EMail;

    @FXML
    private Label status;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        factoryInitialization();

        userImage.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.MOVE);
            event.consume();
        });

        userImage.setOnDragDropped(event -> {
            try {
                userImage.setImage(new Image(event.getDragboard().getFiles().get(0).toURI().toURL().toString()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            event.setDropCompleted(true);
        });
    }



    private void factoryInitialization(){

        ControllersDataFactory controllersDataFactory  = ControllersDataFactory.getLink();

        Map<String,Object> data = (HashMap<String,Object>)controllersDataFactory.get(CurrentUser.class);

        if(data == null){
            data = new HashMap<>();
            data.put("name",nameLabel.getText());
            data.put("phone",phoneNumber.getText());
            data.put("EMail",EMail.getText());
           // data.put("status",status.getText());

            ControllersDataFactory.getLink().add(this.getClass(),data);
        }else{
            nameLabel.setText((String) data.get("name"));
            phoneNumber.setText((String) data.get("phoneNumber"));
            EMail.setText((String) data.get("email"));
            status.setText((String) data.get("status"));
        }
    }
}
