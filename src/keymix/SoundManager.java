package keymix;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Jessie on 2/27/2016.
 *
 * Sound Manager window
 *     - Allows user to upload and name sound files
 */
public class SoundManager extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // loads FXML for SoundManager
        Parent root = FXMLLoader.load(getClass().getResource("SoundManager.fxml"));
        Scene scene = new Scene(root, 640, 400);

        // sets up and launches window
        primaryStage.setTitle("keymix . sound manager");
        primaryStage.setScene(scene);
        primaryStage.show();



    }
}
