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

        AudioClip sample1 = new AudioClip(new File("samples\\AnantSahai.wav").toURI().toString());
        AudioClip sample2 = new AudioClip(new File("samples\\EECSforLifeYO.wav").toURI().toString());
        AudioClip sample3 = new AudioClip(new File("samples\\ImBetterThanYou.wav").toURI().toString());
        AudioClip sample4 = new AudioClip(new File("samples\\PaulHilfinger.wav").toURI().toString());
        AudioClip sample5 = new AudioClip(new File("samples\\SixFigsRespeck.wav").toURI().toString());

        TextArea txt = new TextArea();
        txt.setWrapText(true);
        txt.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.Z) {
                sample1.play();
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.X) {
                sample2.play();
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.C) {
                sample3.play();
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.V) {
                sample4.play();
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.B) {
                sample5.play();
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
                sample1.stop();
                sample2.stop();
                sample3.stop();
                sample4.stop();
                sample5.stop();
                keyEvent.consume();
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(txt);
        primaryStage.setScene(new Scene(root, 800, 450));
        primaryStage.show();
    }
}