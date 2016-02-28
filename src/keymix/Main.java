package keymix;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("app.fxml"));
        primaryStage.setTitle("keymix.");
        Scene scene = new Scene(root, 640, 400);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(Main.class.getResource("app.css").toExternalForm());
        primaryStage.getIcons().add(new Image(new File("assets\\km.gif").toURI().toString()));
                //getClass().getResourceAsStream("km.gif")));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
