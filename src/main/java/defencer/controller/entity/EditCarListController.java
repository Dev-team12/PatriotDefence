package defencer.controller.entity;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import defencer.model.Car;
import defencer.model.Project;
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
    private TableView<Car> tableCars;
    @FXML
    private TableColumn<Car, String> carName;
    @FXML
    private JFXComboBox<String> comboSelectCar;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnOk;

    private List<Car> freeCar;
    private Project project;
    private ObservableList<Car> observableCar = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadCarTable();

        btnOk.setOnAction(e -> root.getScene().getWindow().hide());

        comboSelectCar.setItems(FXCollections
                .observableArrayList(getFreeCars()));

        btnAdd.setOnAction(e -> addCar());
        btnDelete.setOnAction(e -> delete());
    }

    /**
     * Add car to project.
     */
    private void addCar() {
        final String value = comboSelectCar.getValue();
        freeCar.forEach(s -> {
            if (s.getCarName().equals(value)) {
                s.setStatus("BUSY");
                s.setProjectId(project.getId());
                try {
                    ServiceFactory.getWiseacreService().updateEntity(s);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        loadCars(project);
    }

    /**
     * Load selected instructor table.
     */
    void loadCars(Project project) {
        this.project = project;
        observableCar.clear();
        observableCar.addAll(getCurrentCar(project.getId()));
        tableCars.setItems(observableCar);
    }

    /**
     * @return list of cars that were selected before.
     */
    private List<Car> getCurrentCar(Long projectId) {
        return ServiceFactory.getWiseacreService().getCurrentCar(projectId);
    }

    /**
     * @return free car's name for project.
     */
    private List<String> getFreeCars() {
        freeCar = ServiceFactory.getWiseacreService().getFreeCar();
        List<String> list = new LinkedList<>();
        freeCar.forEach(s -> list.add(s.getCarName()));
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
        final Car car = tableCars.getSelectionModel().getSelectedItem();
        if (car == null) {
            return;
        }
        tableCars.getItems().remove(car);
        ServiceFactory.getWiseacreService().deleteSelectedCar(car.getId(), tableCars.getItems(), project);
        loadCars(project);
    }
}
