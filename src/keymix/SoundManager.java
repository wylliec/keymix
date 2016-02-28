package keymix;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jessie on 2/27/2016.
 *
 * Sound Manager window
 *     - Allows user to upload and name sound files
 */
public class SoundManager extends Application {
    // list of sound files added by user for key mappings
    LinkedList<File> soundFiles = new LinkedList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();

        Button buttonLoad = new Button("Load");
        buttonLoad.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e) {
                FileChooser fileChooser = new FileChooser();

                // adds extension filters
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3"),
                        new FileChooser.ExtensionFilter("MIDI files (*.midi)", "*.midi")
                );

                // lets user pick file(s)
                List<File> fileList = fileChooser.showOpenMultipleDialog(primaryStage);

                // adds fileList to soundFiles
                soundFiles.addAll(fileList);

                System.out.println(soundFiles);
            }
        });
        VBox vBox = VBoxBuilder.create()
                .children(buttonLoad)
                .build();
        root.getChildren().add(vBox);
        Scene scene = new Scene(root, 640, 400);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(Main.class.getResource("app.css").toExternalForm());
        primaryStage.show();



//        // OLD CODE FOR CUSTOM/BASIC FILE BROWSER
//        // creates tree pane
//        VBox treeBox = new VBox();
//        treeBox.setPadding(new Insets(10,10,10,10));
//        treeBox.setSpacing(10);
//
//        // sets up the file browser root
//        String hostName = "computer";
//        try {
//            hostName = InetAddress.getLocalHost().getHostName();
//        } catch (UnknownHostException x) {}
//
//        TreeItem<String> rootNode = new TreeItem<>(hostName);
//
//        // lists all drives on system, adds to the root node
//        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();
//        for (Path name : rootDirectories){
//            FilePathTreeItem treeNode = new FilePathTreeItem(name);
//            rootNode.getChildren().add(treeNode);
//        }
//        rootNode.setExpanded(true);
//
//        // creates the tree view
//        treeView = new TreeView<>(rootNode);
//
//        // adds everything to the tree pane
//        treeBox.getChildren().addAll(new Label("File browser"), treeView);
//        VBox.setVgrow(treeView, Priority.ALWAYS);
//
//        // sets up and shows window
//        primaryStage.setTitle("Sound Manager");
//        StackPane root = new StackPane();
//        root.getChildren().addAll(treeBox);
//        primaryStage.setScene(new Scene(root, 640, 400));
//        primaryStage.show();

    }
}
