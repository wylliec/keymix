package keymix;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.*;

public class Controller {
    private Set<File> importedSounds = new TreeSet<>();
    private Map<KeyCode, AudioClip>[] maps = new Map[10];
    private int myMap = 0;

    @FXML private Button importFile;

    @FXML protected void keyPress(KeyEvent keyEvent) {
        String id = keyEvent.getText().toUpperCase();

        if (maps[myMap] != null && keyEvent.getCode() == KeyCode.BACK_SPACE) {
            maps[myMap].values().stream().forEach(clip -> clip.stop());
        }

        if(maps[myMap] != null && id.length() == 1 && Character.isLetter(id.charAt(0))
                && maps[myMap].containsKey(keyEvent.getCode())) {
            importFile.getScene().lookup("#" + id).getStyleClass().add("pressed");
            maps[myMap].get(keyEvent.getCode()).play();
        }
        keyEvent.consume();
    }

    @FXML protected void keyRelease(KeyEvent keyEvent) {
        //String id = keyEvent.getText();
        String id = keyEvent.getText().toUpperCase();
        //System.out.println(id);
        if(maps[myMap] != null) {
            importFile.getScene().lookup("#" + id).getStyleClass().remove("pressed");
        }
        keyEvent.consume();
    }

    @FXML protected void importFiles(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.midi");
        fileChooser.getExtensionFilters().add(extFilter);

        List<File> files = fileChooser.showOpenMultipleDialog(importFile.getScene().getWindow());
        if(files != null) {
            importedSounds.addAll(files);
        }
    }

    @FXML protected void removeFiles(ActionEvent event) {
        ChoiceDialog<File> dialog = new ChoiceDialog<>(new File("Select an option."), importedSounds);
        dialog.setTitle("Delete Sample");
        dialog.setHeaderText("Choose what sample you would like delete");
        dialog.setContentText("Sample:");

        Optional<File> result = dialog.showAndWait();
        result.ifPresent(file -> importedSounds.remove(file));

        event.consume();
    }

    @FXML protected void clickLetter(ActionEvent event) {
        String id = ((Node)event.getTarget()).idProperty().getValue();

        ChoiceDialog<File> dialog = new ChoiceDialog<>(new File("Select an option."), importedSounds);
        dialog.setTitle("Choose Sample");
        dialog.setHeaderText("Choose what sample you would like to map to letter " + id);
        dialog.setContentText("Sample:");

        Optional<File> result = dialog.showAndWait();
        result.ifPresent(file -> this.mapKey(file, id));

        event.consume();
    }

    @FXML protected void clickNumber(ActionEvent event) {
        String id = ((Node)event.getTarget()).idProperty().getValue();
        myMap = id.charAt(0) - '0';
        event.consume();
    }

    private void mapKey(File file, String id) {
        if(maps[myMap] == null) {
            maps[myMap] = new HashMap<>();
        }
        maps[myMap].put(KeyCode.valueOf(id), new AudioClip(file.toURI().toString()));
    }
}
