package defencer.controller.entity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import defencer.controller.update.UpdateApprenticeController;
import defencer.model.Apprentice;
import defencer.service.factory.ServiceFactory;
import defencer.util.NotificationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import jfxtras.scene.control.ImageViewButton;
import lombok.SneakyThrows;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Igor Gnes on 4/12/17.
 */
public class ApprenticeController implements Initializable {

    @FXML
    private TableView<Apprentice> table;
    @FXML
    private TableColumn<Apprentice, String> name;
    @FXML
    private TableColumn<Apprentice, String> email;
    @FXML
    private TableColumn<Apprentice, String> phone;
    @FXML
    private TableColumn<Apprentice, String> occupation;
    @FXML
    private TableColumn<Apprentice, String> project;
    @FXML
    private JFXComboBox<String> comboProject;
    @FXML
    private JFXButton btnAddOneMore;
    @FXML
    private JFXButton btnFind;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnEdit;
    @FXML
    private ImageViewButton btnUpdate;
    @FXML
    private JFXDatePicker datePeriod;

    private ObservableList<Apprentice> observableApprentices = FXCollections.observableArrayList();
    private static final Long DEFAULT_PERIOD = 30L;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        insertApprenticeTable();
        loadApprentice();

        comboProject.setItems(FXCollections
                .observableArrayList(getProjectName()));
        comboProject.setValue("");

        btnAddOneMore.setOnAction(this::newApprentice);

        btnDelete.setOnAction(e -> deleteApprentice());

        btnUpdate.setOnMouseClicked(e -> loadApprentice());

        btnFind.setOnAction(e -> search());

        table.setOnMouseClicked(event -> {
            if (event.getClickCount() >= 2) {
                editApprentice(event);
            }
        });
    }

    /**
     * Search apprentice with given parameters.
     */
    private void search() {
        if (datePeriod.getValue() == null) {
            datePeriod.setValue(LocalDate.now().minusDays(DEFAULT_PERIOD));
        }
        Long days = ChronoUnit.DAYS.between(datePeriod.getValue(), LocalDate.now());
        final List<Apprentice> apprentices = ServiceFactory.getApprenticeService().findByPeriod(days, comboProject.getValue());
        observableApprentices.clear();
        observableApprentices.addAll(apprentices);
        table.setItems(observableApprentices);
    }


    /**
     * Load instructors into table.
     */
    private void loadApprentice() {
        observableApprentices.clear();
        observableApprentices.addAll(getApprentice());
        table.setItems(observableApprentices);
    }

    /**
     * Insert value for table.
     */
    private void insertApprenticeTable() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        occupation.setCellValueFactory(new PropertyValueFactory<>("occupation"));
        project.setCellValueFactory(new PropertyValueFactory<>("projectName"));
    }

    /**
     * Open new page to add one more pupil.
     * {@link SneakyThrows} here because i am totally sure that path to fxml id correct.
     */
    @SneakyThrows
    private void newApprentice(ActionEvent event) {
        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/entity/add/NewApprentice.fxml"));
        Parent parent = fxmlLoader.load();

        final Stage stage = new Stage();
        Scene value = new Scene(parent);
        value.getStylesheets().add("css/main.css");
        stage.setScene(value);
        stage.initModality(Modality.WINDOW_MODAL);
        Window window = ((Node) event.getSource()).getScene().getWindow();
        stage.initOwner(window);
        stage.show();

        stage.setOnHiding(e -> loadApprentice());
    }

    /**
     * Opens page for editing selected parameters.
     */
    @SneakyThrows
    private void editApprentice(MouseEvent event) {
        final Apprentice apprentice = table.getSelectionModel().getSelectedItem();
        if (apprentice == null) {
            NotificationUtil.warningAlert("Warning", "Select apprentice first", NotificationUtil.SHORT);
            return;
        }
        final FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/entity/update/UpdateApprentice.fxml"));
        Parent parent = fxmlLoader.load();
        UpdateApprenticeController updateApprenticeController = fxmlLoader.getController();
        updateApprenticeController.editCurrentApprentice(apprentice);

        final Stage stage = new Stage();
        Scene value = new Scene(parent);
        stage.setScene(value);
        value.getStylesheets().add("css/main.css");
        stage.initModality(Modality.WINDOW_MODAL);
        Window window = ((Node) event.getSource()).getScene().getWindow();
        stage.initOwner(window);
        stage.show();

        stage.setOnHiding(e -> loadApprentice());
    }

    /**
     * @return list of apprentice for last months.
     */
    private List<Apprentice> getApprentice() {
        return ServiceFactory.getApprenticeService().getApprenticeLastMonths();
    }

    /**
     * Deletes selected apprentice.
     */
    private void deleteApprentice() {
        final Apprentice apprentice = table.getSelectionModel().getSelectedItem();
        if (apprentice == null) {
            NotificationUtil.warningAlert("Warning", "Select apprentice firstly", NotificationUtil.SHORT);
            return;
        }
        try {
            ServiceFactory.getApprenticeService().deleteEntity(apprentice);
            observableApprentices.clear();
            loadApprentice();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return all type of available projects.
     */
    private List<String> getProjectName() {
        return ServiceFactory.getWiseacreService().getAvailableProject();
    }
}
