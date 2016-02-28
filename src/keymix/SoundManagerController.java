package keymix;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Jessie on 2/27/2016.
 *
 * Controller for SoundManager window
 */
public class SoundManagerController implements Initializable {
    // TODO: PRESERVE MAPPINGS BETWEEN CONTROLLERS

    private MainController main;
    private KeymixAppController parentController;
    private ImportedSoundManager soundFilesMap = ImportedSoundManager.getInstance();

    @FXML private ObservableList<SoundFileWithName> soundFilesTabData = FXCollections.observableArrayList();

    // JavaFX objects
    @FXML private TableView<SoundFileWithName> soundFilesTableView = new TableView<>();
    @FXML private TableColumn soundNameCol = new TableColumn("sound name");
    @FXML private TableColumn soundFilePathCol = new TableColumn("sound file path");
    @FXML private Button loadSoundsButton;
    @FXML private Button returnButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // initializes soundFilesMap
        for (String key : soundFilesMap.getMap().keySet()) {
            String name = key;
            String path = soundFilesMap.get(key);

            soundFilesTabData.add(new SoundFileWithName(name, path));
        }

        // TODO: set up listener for changes
        soundFilesTableView.setItems(soundFilesTabData);

        // sets up cell value factories for soundFilesTableView columns
        soundNameCol.setCellValueFactory(new PropertyValueFactory<SoundFileWithName, String>("soundName"));
        soundFilePathCol.setCellValueFactory(new PropertyValueFactory<SoundFileWithName,String>("soundPath"));

        // make soundNameCol editable
        soundNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        soundNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<SoundFileWithName, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<SoundFileWithName, String> t) {
                        // handles edits to sound name after enter key is pressed
                        // TODO: switch to saving edits after switching focus, see http://docs.oracle.com/javafx/2/ui_controls/table-view.htm for ideas

                        SoundFileWithName soundFile = t.getTableView().getItems().get(t.getTablePosition().getRow());
                        String newName = t.getNewValue();
                        String oldName = soundFile.getSoundName();
                        String currPath = soundFile.getSoundPath();

                        // TODO: make checking for duplicates one private method to reduce repetition
                        // checks for duplicates -------------------------------------------------------------------------------
                        String existingPath = soundFilesMap.get(newName);

                        // checks if:
                        //      - the sound files hash map is empty
                        //      - existingPath is null
                        // if either is false, then the user is trying to add a sound file under a duplicate name
                        // and this alerts the user of error
                        if (!soundFilesMap.getMap().isEmpty() && existingPath != null) {
                            errorAlert("Duplicate Name Error",
                                       "You tried to change a sound's name to a name that already existed!" +
                                       "\n\nPlease try again with a new name.");

                            // TODO: figure out how to immediately change back to oldName after user tries to rename

                        } else {
                            // renames soundFile
                            soundFile.setSoundName(newName);

                            // updates hash map of sound file names and paths
                            soundFilesMap.getMap().remove(oldName);
                            soundFilesMap.put(newName, currPath);
                        }
                    }// end of handle method
                }
        );
    }

    public void init(MainController mainController) {
        main = mainController;
    }

    // loads sounds into SoundManager window into a table
    @FXML protected void handleLoadSoundsButtonAction(ActionEvent e){
        FileChooser fileChooser = new FileChooser();

        // adds extension filters
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3"),
                new FileChooser.ExtensionFilter("MIDI files (*.midi)", "*.midi"),
                new FileChooser.ExtensionFilter("WAV files (*.wav)", "*.wav")
        );

        // lets user pick file(s)
        List<File> fileList = fileChooser.showOpenMultipleDialog(loadSoundsButton.getScene().getWindow());

        if (fileList != null) {
            for (File f : fileList) {
                // TODO: check for duplicate keys

                // get data from file
                String name = f.getName();
                String path = f.getAbsolutePath();


                // checks for duplicates -------------------------------------------------------------------------------
                String existingPath = soundFilesMap.get(name);

                // checks if:
                //      - the sound files hash map is empty
                //      - existingPath is null
                // if either is false, then the user is trying to add a sound file under a duplicate name
                // and this alerts the user of error
                if (!soundFilesMap.getMap().isEmpty() && existingPath != null) {
                    errorAlert("Duplicate Name Error",
                               "You tried adding a sound file under a sound file name that already existed!" +
                               "\n\nPlease try again with a new name.");
                }

                // prevents user from adding the same file twice
                else if (path.equals(existingPath)) {
                    errorAlert("Duplicate File Error", "You tried to add the same file twice!" +
                                                       "\n\nPlease try again with different files.");
                }

                // otherwise, adds the new sound file
                else {
                    // adds fileList to soundFiles with file name as key, full path as value
                    soundFilesMap.put(name, path);

                    // adds new SoundFileWithName
                    soundFilesTabData.add(new SoundFileWithName(name, path));
                }
            }// end of for-loop
        }

        // gives soundFilesTableView the updated information on soundFiles
        soundFilesTableView.setItems(soundFilesTabData);
    }

    // deletes selected sound
    @FXML protected void handleDeleteSelectedButtonAction(ActionEvent e) {
        // TODO: possibly implement delete buttons for each row, see https://gist.github.com/abhinayagarwal/9735744 for ideas

        // if table data is not empty
        if (!soundFilesTabData.isEmpty()) {
            // gets selected item in the TableView
            SoundFileWithName soundFile = soundFilesTableView.getSelectionModel().getSelectedItem();

            // removes item from the data for the TableView
            soundFilesTabData.remove(soundFile);

            // removes associated item from the hash map
            soundFilesMap.getMap().remove(soundFile.getSoundName());
        }
    }

    // closes current window
    @FXML protected void handleReturnButtonAction(ActionEvent event) {
        try {
            //main.setImportedSoundsForKeymixApp(soundFilesMap);

            // sets up and displays KeymixAppController window
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("app.fxml"));
            Parent root = fxmlLoader.load();

            KeymixAppController managerController = fxmlLoader.<KeymixAppController> getController();
            managerController.setImportedSounds(soundFilesMap.getMap());

            // sets up scene and shows it
            Scene scene = new Scene(root, 640, 400);
            scene.getStylesheets().add(Main.class.getResource("app.css").toExternalForm());

            // sets up stage and shows it
            Stage stage = new Stage();
            stage.setTitle("keymix.");
            stage.setScene(scene);
            stage.show();

            // closes current window
            Stage currStage = (Stage) returnButton.getScene().getWindow();
            currStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public HashMap<String, String> getSoundFilesMap() {
        return soundFilesMap.getMap();
    }

    public void setSoundFilesMap(HashMap<String, String> hm) {
        this.soundFilesMap.setMap(hm);
    }

    public void setParentController(KeymixAppController c) {
        this.parentController = c;
    }

    // displays an error alert dialog to the user
    private void errorAlert(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    // class for soundFilesTableView containing information on sound files' name and file path
    public static class SoundFileWithName {
        private final SimpleStringProperty soundName;
        private final SimpleStringProperty soundPath;

        private SoundFileWithName(String name, String path) {
            this.soundName = new SimpleStringProperty(name);
            this.soundPath = new SimpleStringProperty(path);
        }

        public String getSoundName() {
            return soundName.get();
        }
        public void setSoundName(String name) {
            soundName.set(name);
        }

        public String getSoundPath() {
            return soundPath.get();
        }
        public void setSoundPath(String path) {
            soundPath.set(path);
        }

    }
}
