package keymix;

/**
 * Created by Caleb on 2/27/2016.
 * "If the drums don't bang, the beat don't bang..."
 */
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;

public class Play extends Application{

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Play!");

        String musicURL = "C:\\Users\\Caleb\\Music\\I Smoke Weed - Chrome.mp3";     // For example
        String musicFile = new File(musicURL).toURI().toString();
        String musicFile2 = new File("certifiedgz.mp3").toURI().toString();

        AudioClip kick1 = new AudioClip(new File("AnantSahai.wav").toURI().toString());
        AudioClip kick2 = new AudioClip(new File("EECSforLifeYO.wav").toURI().toString());
        AudioClip kick3 = new AudioClip(new File("ImBetterThanYou.wav").toURI().toString());
        AudioClip kick4 = new AudioClip(new File("PaulHilfinger.wav").toURI().toString());
        AudioClip kick5 = new AudioClip(new File("SixFigsRespeck.wav").toURI().toString());
        /*
        Media sound = new Media(musicFile);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        */
        AudioClip weed = new AudioClip(musicFile);
        AudioClip gz = new AudioClip(musicFile2);

        TextArea txt = new TextArea();
        txt.setWrapText(true);
        txt.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.Z) {
                kick1.play();
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.X) {
                kick2.play();
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.C) {
                kick3.play();
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.V) {
                kick4.play();
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.B) {
                kick5.play();
                keyEvent.consume();
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(txt);
        primaryStage.setScene(new Scene(root, 800, 450));
        primaryStage.show();
    }
}