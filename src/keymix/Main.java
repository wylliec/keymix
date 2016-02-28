package keymix;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.io.File;
// import java.awt.event.KeyEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("app.fxml"));
        primaryStage.setTitle("keymix.");

        Scene scene = new Scene(root, 640, 400);

        AudioClip sample1 = new AudioClip(new File("samples\\AnantSahai.wav").toURI().toString());
        AudioClip sample2 = new AudioClip(new File("samples\\EECSforLifeYO.wav").toURI().toString());
        AudioClip sample3 = new AudioClip(new File("samples\\ImBetterThanYou.wav").toURI().toString());
        AudioClip sample4 = new AudioClip(new File("samples\\PaulHilfinger.wav").toURI().toString());
        AudioClip sample5 = new AudioClip(new File("samples\\SixFigsRespeck.wav").toURI().toString());

        root.setOnKeyPressed((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.Z) {
                scene.lookup("#Z").getStyleClass().add("pressed");
                sample1.play();
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.X) {
                scene.lookup("#X").getStyleClass().add("pressed");
                sample2.play();
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.C) {
                scene.lookup("#C").getStyleClass().add("pressed");
                sample3.play();
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.V) {
                scene.lookup("#V").getStyleClass().add("pressed");
                sample4.play();
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.B) {
                scene.lookup("#B").getStyleClass().add("pressed");
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

        root.setOnKeyReleased((keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.Z) {
                scene.lookup("#Z").getStyleClass().remove("pressed");
            }
            if (keyEvent.getCode() == KeyCode.X) {
                scene.lookup("#X").getStyleClass().remove("pressed");
            }
            if (keyEvent.getCode() == KeyCode.C) {
                scene.lookup("#C").getStyleClass().remove("pressed");
            }
            if (keyEvent.getCode() == KeyCode.V) {
                scene.lookup("#V").getStyleClass().remove("pressed");
            }
            if (keyEvent.getCode() == KeyCode.B) {
                scene.lookup("#B").getStyleClass().remove("pressed");
            }
        });

        primaryStage.setScene(scene);
        scene.getStylesheets().add(Main.class.getResource("app.css").toExternalForm());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
