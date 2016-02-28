package keymix;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

/**
 * Created by Jessie on 2/27/2016.
 *
 * Controller for SoundManager window
 */
public class SoundManagerController /*implements Initializable*/ {
    // contains the paths of all sound files as strings
    @FXML private ObservableList<String> soundFiles = FXCollections.observableArrayList();

    @FXML private ListView<String> lvFilePaths = new ListView<>();
    @FXML private Button btnLoadSounds;
    @FXML private Button btnApplyMappings;
    @FXML private Button btnCancel;


    @FXML protected void handleLoadSoundsButtonAction(ActionEvent e){
        FileChooser fileChooser = new FileChooser();

        // adds extension filters
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3"),
                new FileChooser.ExtensionFilter("MIDI files (*.midi)", "*.midi"),
                new FileChooser.ExtensionFilter("WAV files (*.wav)", "*.wav")
        );

        // lets user pick file(s)
        List<File> fileList = fileChooser.showOpenMultipleDialog(btnLoadSounds.getScene().getWindow());

        if (fileList != null) {
            // adds fileList to soundFiles
            for (File f : fileList) {
                soundFiles.add(f.toString());
            }
        }

        lvFilePaths.setItems(soundFiles);
    }

    @FXML protected void handleApplyMappingsButtonAction(ActionEvent e) {
        // TODO: implement handling of key maps
    }

    @FXML protected void handleCancelButtonAction(ActionEvent e) {
        // TODO: implement handling of canceling to return to original screen
    }

}
