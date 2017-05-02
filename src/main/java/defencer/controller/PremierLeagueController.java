package defencer.controller;

import com.jfoenix.controls.JFXButton;
import defencer.data.CurrentUser;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Igor Gnes on 4/22/17.
 */
public class PremierLeagueController implements Initializable {

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
    private Text txtDescription;
    @FXML
    private Text txtEmail;
    @FXML
    private Text txtPhone;
    @FXML
    private Text txtQualification;
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
    private final int sleepFirst = 970;
    private final int sleep = 300;

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
     * Going to add instructors.
     */
    private void finish() {
        final ObservableList<Instructor> instructors = tableInstructors.getItems();
        finish(instructors);
    }

    /**
     * Create query for set project id to selected instructors.
     */
    private void finish(List<Instructor> instructors) {

        final StringBuffer stringBuffer = new StringBuffer();
        instructors.forEach(s -> stringBuffer.append(s.getFirstLastName()).append(" "));
        Runnable runnable = () -> instructors.forEach(s -> {
            s.setProjectId(project.getId());
            s.setStatus("EXPECTED");
            project.setInstructors(stringBuffer.toString());
            try {
                ServiceFactory.getInstructorService().updateEntity(s);
                ServiceFactory.getProjectService().updateEntity(project);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        final Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CurrentUser.refresh(CurrentUser.getLink().getEmail());
        NotificationUtil.informationAlert("Success", "Added", NotificationUtil.SHORT);
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
        final Runnable runnable = loadInstructorDetails(freeInstructors.get(counter));
        final Thread thread = new Thread(runnable);
        thread.start();
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
        final Instructor selectedItem = tableInstructors.getSelectionModel().getSelectedItem();
        final ObservableList<Instructor> items = tableInstructors.getItems();
        items.remove(selectedItem);
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
        txtDescription.setText("Description: " + project.getDescription());

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
    private Runnable loadInstructorDetails(Instructor instructor) {
        return () -> {
            try {
                Thread.sleep(sleepFirst);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            txtFirstName.setText("First name: " + instructor.getFirstName());

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            txtLastName.setText("Last name: " + instructor.getLastName());

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            txtQualification.setText("Qualification: " + instructor.getQualification());

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            txtPhone.setText("Phone: " + instructor.getPhone());

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            txtEmail.setText("Email: " + instructor.getEmail());
        };
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
        txtQualification.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
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
        return ServiceFactory.getWiseacreService().getCurrentInstructors(projectId);
    }
}
