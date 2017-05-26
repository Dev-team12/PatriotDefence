package defencer.controller.entity;

import defencer.controller.AskFormController;
import defencer.controller.MainActivityController;
import defencer.controller.update.UpdateInstructorController;
import defencer.data.ControllersDataFactory;
import defencer.data.CurrentUser;
import defencer.model.Instructor;
import defencer.model.enums.Role;
import defencer.service.factory.ServiceFactory;
import defencer.util.NotificationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller provides api for creating, getting, editing and deleting instructors.
 *
 * @author Igor Gnes on 4/6/17.
 */
public class InstructorController implements Initializable {

    @FXML
    private TableView<Instructor> table;
    @FXML
    private TableColumn<Instructor, String> firstName;
    @FXML
    private TableColumn<Instructor, String> lastName;
    @FXML
    private TableColumn<Instructor, String> email;
    @FXML
    private TableColumn<Instructor, String> phone;
    @FXML
    private TableColumn<Instructor, String> qualification;
    @FXML
    private TableColumn<Instructor, String> telegramId;
    @FXML
    private TableColumn<Instructor, String> role;

    private ObservableList<Instructor> observableInstructors = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        insertTableInstructors();
        loadInstructors();
        showSmartBar();
        table.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 2) {
                editInstructor(event);
            }
        });
    }

    /**
     * Showing smart bar with image buttons add, delete, update.
     */
    private void showSmartBar() {
        MainActivityController mainActivityController = (MainActivityController) ControllersDataFactory.getLink().get(MainActivityController.class, "class");
        mainActivityController.showSmartToolbar();
        mainActivityController.getUpdateAction().setOnMouseClicked(e -> loadInstructors());
        mainActivityController.getBtnAddEvent().setVisible(false);
        mainActivityController.getPdfExportAction().setOnMouseClicked(e -> pdfReport());
        mainActivityController.getBtnExcel().setOnMouseClicked(e -> excelReport());
        if (!Role.CHIEF_OFFICER.equals(CurrentUser.getLink().hasRole())) {
            mainActivityController.getAddAction().setVisible(false);
            mainActivityController.getDeleteAction().setVisible(false);
        } else {
            mainActivityController.getDeleteAction().setOnMouseClicked(e -> deleteInstructor());
            mainActivityController.getAddAction().setOnMouseClicked(this::newInstructor);
        }
    }

    /**
     * Opens page for editing selected parameters.
     */
    @SneakyThrows
    private void editInstructor(MouseEvent event) {
        final Instructor instructor = table.getSelectionModel().getSelectedItem();
        if (instructor == null) {
            NotificationUtil.warningAlert("Warning", "Select instructor first", NotificationUtil.SHORT);
            return;
        }
        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/entity/update/UpdateInstructor.fxml"));
        Parent parent = fxmlLoader.load();
        UpdateInstructorController updateInstructorController = fxmlLoader.getController();
        updateInstructorController.editCurrentInstructor(instructor);

        final Stage stage = new Stage();
        Scene value = new Scene(parent);
        value.getStylesheets().add("css/main.css");
        stage.setScene(value);
        stage.initModality(Modality.WINDOW_MODAL);
        Window window = ((Node) event.getSource()).getScene().getWindow();
        stage.initOwner(window);
        stage.show();

        stage.setOnHiding(e -> {
            loadInstructors();
            if (CurrentUser.getLink().getEmail().equals(instructor.getEmail())) {
                CurrentUser.refresh(instructor.getEmail());
            }
        });
    }

    /**
     * Load instructors into table.
     */
    private void loadInstructors() {
        observableInstructors.clear();
        observableInstructors.addAll(getInstructors());
        table.setItems(observableInstructors);
    }

    /**
     * Insert values for table.
     */
    private void insertTableInstructors() {
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        qualification.setCellValueFactory(new PropertyValueFactory<>("qualification"));
        telegramId.setCellValueFactory(new PropertyValueFactory<>("telegramId"));
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

    /**
     * Deletes selected instructor.
     */
    private void deleteInstructor() {
        final Instructor instructor = table.getSelectionModel().getSelectedItem();
        if (instructor == null) {
            NotificationUtil.warningAlert("Warning", "Select instructor firstly", NotificationUtil.SHORT);
            return;
        }
        if (instructor.getEmail().equals(CurrentUser.getLink().getEmail())) {
            NotificationUtil.warningAlert("Warning", "Sorry, you can't delete yourself ", NotificationUtil.SHORT);
            return;
        }
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/askForm.fxml"));
            Parent parent = fxmlLoader.load();

            final Stage stage = new Stage();
            Scene value = new Scene(parent);
            value.getStylesheets().add("css/main.css");
            stage.setScene(value);
            stage.initModality(Modality.WINDOW_MODAL);
            Window window = table.getScene().getWindow();
            stage.initOwner(window);
            stage.show();

            stage.setOnHiding(event -> {
                if ((boolean) ControllersDataFactory.getLink().get(AskFormController.class, "isDelete")) {
                    ServiceFactory.getInstructorService().deleteInstructor(instructor.getId());
                    observableInstructors.clear();
                    loadInstructors();
                }
            });

        } catch (Exception e) {
            NotificationUtil.errorAlert("Error", "Can't delete", NotificationUtil.SHORT);
        }
    }

    /**
     * @return list of instructors.
     */
    private List<Instructor> getInstructors() {
        return ServiceFactory.getInstructorService().getInstructors();
    }

    /**
     * Open new page to add one more instructor.
     * {@link SneakyThrows} here because i am totally sure that path to fxml is correct.
     */
    @SneakyThrows
    private void newInstructor(MouseEvent event) {
        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/entity/add/NewInstructor.fxml"));
        Parent parent = fxmlLoader.load();

        final Stage stage = new Stage();
        Scene value = new Scene(parent);
        value.getStylesheets().add("css/main.css");
        stage.setScene(value);
        stage.initModality(Modality.WINDOW_MODAL);
        Window window = ((Node) event.getSource()).getScene().getWindow();
        stage.initOwner(window);
        stage.show();

        stage.setOnHiding(e -> loadInstructors());
    }

    /**
     * Preparing pdf report for instructor in table.
     */
    private void pdfReport() {
        if (table.getItems().isEmpty()) {
            NotificationUtil.warningAlert("Warning", "Nothing to export", NotificationUtil.SHORT);
            return;
        }
        ServiceFactory.getPdfService().instructorReport(table.getItems());
    }

    /**
     * Preparing excel report for instructor in table.
     */
    private void excelReport() {
        if (table.getItems().isEmpty()) {
            NotificationUtil.warningAlert("Warning", "Nothing to export", NotificationUtil.SHORT);
            return;
        }
        ServiceFactory.getExcelService().instructorReport(table.getItems());
    }
}