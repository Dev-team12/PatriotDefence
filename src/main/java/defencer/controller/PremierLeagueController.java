package defencer.controller;

import com.jfoenix.controls.JFXButton;
import defencer.model.Instructor;
import defencer.model.Project;
import defencer.service.factory.ServiceFactory;
import defencer.util.NotificationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import jfxtras.scene.control.ImageViewButton;

import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * @author Igor Gnes on 4/22/17.
 */
public class PremierLeagueController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Text txtProjectName;
    @FXML
    private Text txtPlace;
    @FXML
    private Text txtDateStart;
    @FXML
    private Text txtDateFinish;
    @FXML
    private Text txtAuthor;
    @FXML
    private Text txtLastName;
    @FXML
    private Text txtFirstName;
    @FXML
    private TableView<Instructor> tableInstructors;
    @FXML
    private TableColumn<Instructor, String> instructors;
    @FXML
    private ImageViewButton btnAdd;
    @FXML
    private JFXButton btnFinish;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private MediaView leagueInstructors;
    @FXML
    private ImageViewButton btnNext;
    @FXML
    private ImageViewButton btnPrevious;

    private ObservableList<Instructor> observableInstructors = FXCollections.observableArrayList();
    private List<Instructor> freeInstructors;
    private Set<Instructor> instructorSet = new HashSet<>();
    private Project project;
    private int counter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnNext.setOnMouseClicked(e -> nextInstructor());
        btnPrevious.setOnMouseClicked(e -> previousInstructor());
        btnAdd.setOnMouseClicked(e -> addInstructor());
        btnDelete.setOnAction(s -> deleteSelectedInstructor());
        btnFinish.setOnAction(s -> finish());
    }

    private void firstThing() {
        insertInstructorTable();
        loadThread();
        play(freeInstructors.get(counter).getVideoPath());
    }

    /**
     * Create query for set project id to selected instructors.
     */
    private void finish() {
        ServiceFactory.getInstructorService().configureProject(tableInstructors.getItems(), project);
        root.getScene().getWindow().hide();
    }

    /**
     * Load Thread for show instructor details with a little pause.
     */
    private void loadThread() {
        if (freeInstructors.isEmpty()) {
            NotificationUtil.warningAlert("Warning", "All instructors are busy", NotificationUtil.SHORT);
            return;
        }
        if (counter >= freeInstructors.size() || counter < 0) {
            counter = 0;
        }
        loadInstructorDetails(freeInstructors.get(counter));
    }

    /**
     * Toggle previous instructor.
     */
    private void previousInstructor() {
        counter -= 1;
        clearTxt();
        loadThread();
        play(freeInstructors.get(counter).getVideoPath());
    }

    /**
     * Toggle next instructor.
     */
    private void nextInstructor() {
        counter += 1;
        clearTxt();
        loadThread();
        play(freeInstructors.get(counter).getVideoPath());
    }

    /**
     * Delete selected instructor.
     */
    private void deleteSelectedInstructor() {
        final Instructor instructor = tableInstructors.getSelectionModel().getSelectedItem();
        tableInstructors.getItems().remove(instructor);
    }

    /**
     * Load project details.
     */
    public void loadProjectDetails(Project project, List<Instructor> freeInstructors) {
        this.project = project;
        this.freeInstructors = freeInstructors;
        txtProjectName.setText("ProjectName: " + project.getNameId());
        txtPlace.setText("Place: " + project.getPlace());
        txtDateStart.setText("DateStart: " + project.getDateStart());
        txtDateFinish.setText("DateFinish: " + project.getDateFinish());
        txtAuthor.setText("Author: " + project.getAuthor());
        firstThing();
    }

    /**
     * Configured table.
     */
    private void insertInstructorTable() {
        instructors.setCellValueFactory(new PropertyValueFactory<>("firstLastName"));
    }

    /**
     * Add instructor in table.
     */
    private void addInstructor() {
        instructorSet.add(freeInstructors.get(counter));
        observableInstructors.clear();
        observableInstructors.addAll(instructorSet);
        tableInstructors.setItems(observableInstructors);
    }

    /**
     * Set instructor details with a little pause.
     *
     * @param instructor is selected instructor.
     */
    private void loadInstructorDetails(Instructor instructor) {
        txtFirstName.setText("First name: " + instructor.getFirstName());
        txtLastName.setText("Last name: " + instructor.getLastName());
    }

    /**
     * @param path is path to video.
     */
    private void play(String path) {
        if (path == null) {
            return;
        }
        try {
            final String absolutePath = new File(path).getAbsolutePath();
            final Media media = new Media(new File(absolutePath).toURI().toString());
            final MediaPlayer mediaPlayer = new MediaPlayer(media);
            leagueInstructors.setMediaPlayer(mediaPlayer);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setMute(true);
        } catch (MediaException e) {
            //NON
        }
    }

    /**
     * Clear text with instructors details.
     */
    private void clearTxt() {
        txtFirstName.setText("");
        txtLastName.setText("");
    }
}
