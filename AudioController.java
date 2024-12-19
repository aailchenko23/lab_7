package com.example.demo72;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class AudioController implements Initializable {
    @FXML
    private Slider volume;

    @FXML
    private ProgressBar audioProgress;

    @FXML
    private Label label_audio;

    @FXML
    private Button pauseButton;
    @FXML
    private Button playButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button previousButton;

    private File file;
    private File[] files;
    private Media media;
    private MediaPlayer mediaPlayer;


    private ArrayList<File> songs;
    private int songNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        songs = new ArrayList<>();
        URL musicFolderURL = getClass().getResource("/com/example/demo72/music");
        if (musicFolderURL != null) {
            File musicFolder = new File(musicFolderURL.getFile());
            System.out.println("Шлях до папки music: " + musicFolder.getAbsolutePath());

            File[] files = musicFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));
            if (files != null && files.length > 0) {
                for (File file : files) {
                    songs.add(file);
                    System.out.println("Файл знайдено: " + file.getName());
                }
            } else {
                System.out.println("Папка music порожня або файли не знайдені!");
            }
        } else {
            System.out.println("Папка 'music' не знайдена у ресурсах!");
        }

        if (songs.isEmpty()) {
            label_audio.setText("No audio files found!");
            return;
        }

        songNumber = 0;
        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        volume.valueProperty().addListener((observable, oldValue, newValue) -> {
            mediaPlayer.setVolume(newValue.doubleValue() * 0.01);
        });

        volume.setValue(50);
        mediaPlayer.setVolume(0.5);
    }


    @FXML
    void play(ActionEvent event) {
        mediaPlayer.play();
        label_audio.setText(songs.get(songNumber).getName());

    }

    @FXML
    void pause(ActionEvent event) {
        mediaPlayer.pause();
        label_audio.setText(songs.get(songNumber).getName());

    }

    @FXML
    void next(ActionEvent event) {
        if (songNumber < songs.size() - 1) {
            songNumber++;
            mediaPlayer.stop();

            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            label_audio.setText(songs.get(songNumber).getName());
            mediaPlayer.play();
            label_audio.setText(songs.get(songNumber).getName());
        } else {
            songNumber = 0;
            mediaPlayer.stop();

            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            label_audio.setText(songs.get(songNumber).getName());
            mediaPlayer.play();
            label_audio.setText(songs.get(songNumber).getName());
        }
    }

    @FXML
    void previous(ActionEvent event) {
        if (songNumber > 0) {
            songNumber--;
            mediaPlayer.stop();

            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            label_audio.setText(songs.get(songNumber).getName());
            mediaPlayer.play();
            label_audio.setText(songs.get(songNumber).getName());
        } else {
            songNumber = songs.size() - 1;
            mediaPlayer.stop();

            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            label_audio.setText(songs.get(songNumber).getName());
            mediaPlayer.play();
            label_audio.setText(songs.get(songNumber).getName());
        }
    }
}






