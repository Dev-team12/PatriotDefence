package defencer.controller.entity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import defencer.model.Car;
import defencer.model.Project;
import defencer.model.ScheduleCar;
import defencer.service.factory.ServiceFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Igor Gnes on 5/5/17.
 */
public class EditCarListController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private TableView<ScheduleCar> tableCars;
    @FXML
    private TableColumn<ScheduleCar, String> carName;
    @FXML
    private JFXComboBox<String> comboSelectCar;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnOk;

    private List<Car> freeCars;
    private Project project;
    private ObservableList<ScheduleCar> observableCar = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCarTable();
        btnOk.setOnAction(e -> root.getScene().getWindow().hide());
        btnAdd.setOnAction(e -> addCar());
        btnDelete.setOnAction(e -> delete());
    }

    /**
     * Add car to project.
     */
    private void addCar() {
        final String value = comboSelectCar.getValue();
        freeCars.forEach(s -> {
            if (s.getCarName().equals(value)) {
                ServiceFactory.getWiseacreService().updateScheduleCar(project, s);
            }
        });
        loadCars(project, freeCars);
    }

    /**
     * Load selected instructor table.
     */
    void loadCars(Project project, List<Car> freeCars) {
        this.freeCars = freeCars;
        this.project = project;
        observableCar.clear();
        observableCar.addAll(getCurrentCar(project.getId()));
        tableCars.setItems(observableCar);
        comboSelectCar.setItems(FXCollections
                .observableArrayList(getFreeCars()));
    }

    /**
     * @return list of cars that were selected before.
     */
    private List<ScheduleCar> getCurrentCar(Long projectId) {
        return ServiceFactory.getWiseacreService().getCurrentCar(projectId);
    }

    /**
     * @return free car's name for project.
     */
    private List<String> getFreeCars() {
        List<String> list = new LinkedList<>();
        freeCars.forEach(s -> list.add(s.getCarName()));
        return list;
    }

    /**
     * Insert value for current instructor table.
     */
    private void loadCarTable() {
        carName.setCellValueFactory(new PropertyValueFactory<>("carName"));
    }

    /**
     * Delete selected car that was selected in project before.
     */
    private void delete() {
        ScheduleCar car = tableCars.getSelectionModel().getSelectedItem();
        if (car == null) {
            return;
        }
        tableCars.getItems().remove(car);
        ServiceFactory.getWiseacreService().deleteSelectedCar(project.getId(), car.getId());
        loadCars(project, freeCars);
    }
}
