package defencer.controller;

import com.jfoenix.controls.JFXButton;
import defencer.model.Instructor;
import defencer.model.Project;
import defencer.model.Schedule;
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
import lombok.val;

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
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnFinish;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private MediaView leagueInstructors;
    @FXML
    private JFXButton btnNext;
    @FXML
    private JFXButton btnPrevious;

    private ObservableList<Instructor> observableInstructors = FXCollections.observableArrayList();
    private List<Instructor> freeInstructors;
    private Set<Instructor> instructorSet = new HashSet<>();
    private Project project;
    private int counter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        freeInstructors = getFreeInstructors();
        insertInstructorTable();
        loadThread();
        play(freeInstructors.get(counter).getVideoPath());
        btnNext.setOnAction(e -> nextInstructor());
        btnPrevious.setOnAction(e -> previousInstructor());
        btnAdd.setOnAction(e -> addInstructor());
        btnDelete.setOnAction(s -> deleteSelectedInstructor());
        btnFinish.setOnAction(s -> finish());
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
        ServiceFactory.getWiseacreService().deleteSelectedInstructors(instructor.getId(), project.getId());
        updateTableWithCurrentInstructors();
    }

    /**
     * Load project details.
     */
    public void loadProjectDetails(Project project) {
        this.project = project;
        txtProjectName.setText("ProjectName: " + project.getNameId());
        txtPlace.setText("Place: " + project.getPlace());
        txtDateStart.setText("DateStart: " + project.getDateStart());
        txtDateFinish.setText("DateFinish: " + project.getDateFinish());
        txtAuthor.setText("Author: " + project.getAuthor());

        instructorSet.addAll(getCurrentInstructors(project.getId()));
        observableInstructors.addAll(instructorSet);
        tableInstructors.setItems(observableInstructors);
    }

    /**
     * Configured table.
     */
    private void insertInstructorTable() {
        instructors.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    }

    /**
     * Update current instructors table.
     */
    private void updateTableWithCurrentInstructors() {
        observableInstructors.clear();
        observableInstructors.addAll(getCurrentInstructors(project.getId()));
        tableInstructors.setItems(observableInstructors);
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

    /**
     * @return free instructor's name for project.
     */
    private List<Instructor> getFreeInstructors() {
        return ServiceFactory.getWiseacreService().getFreeInstructors();
    }

    /**
     * @return list of instructors who were selected before.
     */
    private List<Instructor> getCurrentInstructors(Long projectId) {
        final List<Schedule> instructors = ServiceFactory.getWiseacreService().getCurrentInstructors(projectId);
        List<Instructor> currentList = new LinkedList<>();
        instructors.forEach(s -> {
            val instructor = new Instructor();
            instructor.setProjectId(projectId);
            instructor.setId(s.getInstructorId());
            instructor.setFirstName(s.getInstructorName());
            currentList.add(instructor);
        });
        return currentList;
    }
}
