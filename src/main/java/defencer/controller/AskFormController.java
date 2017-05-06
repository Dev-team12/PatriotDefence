package defencer.controller;

import com.jfoenix.controls.JFXButton;
import defencer.data.ControllersDataFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Nikita on 05.05.2017.
 */
public class AskFormController implements Initializable {

    @FXML
    private JFXButton yes;
    @FXML
    private JFXButton no;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Map<String, Object> data = new HashMap<>();

        yes.setOnMouseClicked(event -> {
            data.put("isDelete", true);
            ControllersDataFactory.getLink().update(this.getClass(), data);
            yes.getScene().getWindow().hide();
        });

        no.setOnMouseClicked(event -> {
            data.put("isDelete", false);
            ControllersDataFactory.getLink().update(this.getClass(), data);
            no.getScene().getWindow().hide();
        });
    }
}
