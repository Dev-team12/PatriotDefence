package defencer.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Igor Gnes on 4/22/17.
 */
public class PremierLeagueController implements Initializable {

    @FXML
    private MediaView leagueInstructors;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        play("src/main/resources/video/Igor.mp4");

//        next.setOnAction(e -> play("src/main/resources/video/Igor.mp4"));
    }

    /**
     * @param path is path to video.
     */
    private void play(String path) {
        final String absolutePath = new File(path).getAbsolutePath();
        final Media media = new Media(new File(absolutePath).toURI().toString());
        final MediaPlayer mediaPlayer = new MediaPlayer(media);
        leagueInstructors.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);
    }
}
