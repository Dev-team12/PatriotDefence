package defencer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import defencer.data.CurrentUser;
import defencer.service.factory.ServiceFactory;
import javafx.concurrent.Task;
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
    private JFXTextField firstName;
    @FXML
    private JFXTextField lastName;
    @FXML
    private JFXTextField phone;
    @FXML
    private JFXTextField email;
    @FXML
    private Label status;
    @FXML
    private Label projectName;
    @FXML
    private JFXButton btnYes;
    @FXML
    private JFXButton btnUnfortunatelyNo;
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

    private JFXTextField currentFocusedElement;

    private Task<Void> currentTask;

    private Task<Void> updatingTask;

    private static final int BUTTON_WAITING = 5000;
    private static final int HIBERNATE_WAITING = 10000;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        factoryInitialization();

        dragAndDropInitialization();

        btnYes.setOnAction(e -> agree());

        btnUnfortunatelyNo.setOnAction(e -> disagree());

        firstName.setOnKeyTyped(event -> inputsInitialization(firstName));
        lastName.setOnKeyTyped(event -> inputsInitialization(lastName));
        phone.setOnKeyTyped(event -> inputsInitialization(phone));
        email.setOnKeyTyped(event -> inputsInitialization(email));
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

        dragAndDropArea.getChildren().forEach(s -> s.setVisible(false));

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


    /**
     * Inputs initialization.
     */
    private void inputsInitialization(JFXTextField textField) {

        if (currentFocusedElement != null && !currentFocusedElement.equals(textField) && currentTask != null) {
            currentTask.cancel();
            save(currentFocusedElement);
        } else if (currentFocusedElement != null && currentFocusedElement.equals(textField) && currentTask != null) {
            currentTask.cancel();
        }

        currentFocusedElement = textField;

        currentTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // System.out.println("5 seconds started");
                Thread.sleep(BUTTON_WAITING);
                //System.out.println("5 seconds gone");

                return null;
            }
        };
        new Thread(currentTask).start();
        currentTask.setOnSucceeded(event1 -> {
            //System.out.println("saving");
            save(textField);
        });

    }


    /**
     * Saving userProfile to database.
     */
    private void save(JFXTextField textField) {
        //System.out.println("saving :" + textField.getId() + " with text :" + textField.getText());

        switch (textField.getId()) {
            case "firstName":
                CurrentUser.getLink().withFirstName(textField.getText());
                break;

            case "lastName":
                CurrentUser.getLink().withLastName(textField.getText());
                break;

            case "phone":
                CurrentUser.getLink().withPhoneNumber(textField.getText());
                break;

            case "email":
                CurrentUser.getLink().withEmail(textField.getText());
                break;

            default:
                break;
        }


        if (updatingTask != null && !CurrentUser.getLink().isBusy()) {
            updatingTask.cancel();
            //System.out.println("canceling last");
        }

        //System.out.println("new");

        updatingTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                //System.out.println("10 seconds");
                Thread.sleep(HIBERNATE_WAITING);
                //System.out.println("!!!SAVING HIBERNATE!!!");
                CurrentUser.getLink().save();
                return null;
            }
        };
        new Thread(updatingTask).start();
        updatingTask.setOnSucceeded(event1 -> updatingTask = null);
    }
}
