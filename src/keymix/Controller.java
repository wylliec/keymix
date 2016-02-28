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
import java.util.stream.Collectors;

public class Controller {
    private Set<File> importedSounds = new TreeSet<>();
    private Map<KeyCode, AudioClip>[] maps = new Map[10];
    private int myMap = 0;

    @FXML private Button importFile;

    @FXML protected void keyPress(KeyEvent keyEvent) {
//        System.out.println("property: " + ((Node)keyEvent.getTarget()).idProperty().getValue());
//        System.out.println("getText: " + keyEvent.getText());
//        System.out.println("getCharacter: " + keyEvent.getCharacter());
        String id = keyEvent.getText().toUpperCase();
        if(maps[myMap] != null && id.length() == 1 && Character.isLetter(id.charAt(0))
                && maps[myMap].containsKey(keyEvent.getCode())) {
            importFile.getScene().lookup("#" + id).getStyleClass().add("pressed");
            // System.out.println(maps[myMap]);
            maps[myMap].get(keyEvent.getCode()).play();
            /*
            if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
                sample1.stop();
                sample2.stop();
                sample3.stop();
                sample4.stop();
                sample5.stop();
                keyEvent.consume();
            }
            */
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
        importedSounds.addAll(files);
    }

    @FXML protected void clickLetter(ActionEvent event) {
        String id = ((Node)event.getTarget()).idProperty().getValue();
        event.consume();

        Collection<String> collected = importedSounds.stream().map(file -> file.toURI().toString()).collect(Collectors.toList());;

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Select an option.", collected);
        dialog.setTitle("Choose your sample");
        dialog.setHeaderText("Choose what sample you would like to map to letter " + id);
        dialog.setContentText("Sample:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(str -> this.mapKey(str, id));
    }

    @FXML protected void clickNumber(ActionEvent event) {
        String id = ((Node)event.getTarget()).idProperty().getValue();
        int num = id.charAt(0) - '0';
        myMap = num;
        event.consume();
    }

    private void mapKey(String URL, String id) {
        if(maps[myMap] == null) {
            maps[myMap] = new HashMap<KeyCode, AudioClip>();
        }
        maps[myMap].put(KeyCode.valueOf(id), new AudioClip(URL));
    }
}
