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

//        // OLD CODE FOR FILE PICKER, MOVED TO SoundManagerController.java
//        // list of sound files added by user for key mappings
//        private LinkedList<File> soundFiles = new LinkedList<>();
//
//        Group root = new Group();
//
//        Button btnLoadSounds = new Button("Load Sounds");
//        btnLoadSounds.setOnAction((ActionEvent e) -> {
//                FileChooser fileChooser = new FileChooser();
//
//                // adds extension filters
//                fileChooser.getExtensionFilters().addAll(
//                        new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3"),
//                        new FileChooser.ExtensionFilter("MIDI files (*.midi)", "*.midi"),
//                        new FileChooser.ExtensionFilter("WAV files (*.wav)", "*.wav")
//                );
//
//                // lets user pick file(s)
//                List<File> fileList = fileChooser.showOpenMultipleDialog(primaryStage);
//
//                if (fileList != null) {
//                    // adds fileList to soundFiles
//                    soundFiles.addAll(fileList);
//                }
//
//                System.out.println(soundFiles);
//        });
//
//        VBox vBox = new VBox();
//        vBox.setAlignment(Pos.CENTER);
//        vBox.getChildren().add(btnLoadSounds);
//        root.getChildren().add(vBox);


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
