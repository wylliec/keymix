package keymix;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.*;

public class Controller {
    private Set<File> importedSounds = new TreeSet<>();
    private Map<KeyCode, Sample>[] maps = new Map[10];
    private int myMap = 0;

    private final String DEFAULT_OPTION = "Select an option.";

    @FXML private Text message;

    @FXML protected void keyPress(KeyEvent keyEvent) {
        String id = keyEvent.getText().toUpperCase();

        try {
            switchMap(Integer.parseInt(id));
        } catch(NumberFormatException nfe) { }

        if(keyEvent.getCode() == KeyCode.BACK_SPACE || keyEvent.getCode() == KeyCode.SPACE){
            for(Map<KeyCode, Sample> m : maps) {
                if (m != null) {
                    m.values().stream().forEach(sample -> sample.getClip().stop());
                    message.setText("Stopping playback.");
                }
            }
        }
        if(maps[myMap] != null && id.length() == 1 && Character.isLetter(id.charAt(0))
                && maps[myMap].containsKey(keyEvent.getCode()) && !maps[myMap].get(keyEvent.getCode()).isPlaying()) {
            maps[myMap].get(keyEvent.getCode()).setPlaying(true);
            message.getScene().lookup("#" + id).getStyleClass().add("pressed");
            maps[myMap].get(keyEvent.getCode()).getClip().play();
        }
        keyEvent.consume();
    }

    @FXML protected void keyRelease(KeyEvent keyEvent) {
        String id = keyEvent.getText().toUpperCase();
        if(maps[myMap] != null && id.length() == 1 && Character.isLetter(id.charAt(0))
                && maps[myMap].containsKey(keyEvent.getCode())) {
            maps[myMap].get(keyEvent.getCode()).setPlaying(false);
            message.getScene().lookup("#" + id).getStyleClass().remove("pressed");
        }
        keyEvent.consume();
    }

    @FXML protected void importFiles(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.midi");
        fileChooser.getExtensionFilters().add(extFilter);

        List<File> files = fileChooser.showOpenMultipleDialog(message.getScene().getWindow());
        if(files != null) {
            importedSounds.addAll(files);
            message.setText(files.size() + " file(s) were added.");
        } else {

        }
    }

    @FXML protected void removeFiles(ActionEvent event) {
        ChoiceDialog<File> dialog = new ChoiceDialog<>(new File(DEFAULT_OPTION), importedSounds);
        dialog.setTitle("Delete Sample");
        dialog.setHeaderText("Choose what sample you would like to delete");
        dialog.setContentText("Sample:");

        Optional<File> result = dialog.showAndWait();
        result.ifPresent(file -> {
            if (!file.toString().equals(DEFAULT_OPTION)) {
                importedSounds.remove(file);
                message.setText(file.getName() + " was removed.");
            }
        });

        event.consume();
    }

    @FXML protected void clickNumber(ActionEvent event) {
        String id = ((Node)event.getTarget()).idProperty().getValue();
        switchMap(id.charAt(0) - '0');
        event.consume();
    }

    @FXML protected void clickLetter(ActionEvent event) {
        String id = ((Node)event.getTarget()).idProperty().getValue();

        ChoiceDialog<File> dialog = new ChoiceDialog<>(new File(DEFAULT_OPTION), importedSounds);
        dialog.setTitle("Choose Sample");
        dialog.setHeaderText("Choose what sample you would like to map to letter " + id);
        dialog.setContentText("Sample:");

        Optional<File> result = dialog.showAndWait();
        result.ifPresent(file -> this.mapKey(file, id));

        event.consume();
    }

    private void mapKey(File file, String id) {
        if (!file.toString().equals(DEFAULT_OPTION)) {
            if (maps[myMap] == null) {
                maps[myMap] = new HashMap<>();
            }
            maps[myMap].put(KeyCode.valueOf(id), new Sample(file.toURI().toString()));
            message.getScene().lookup("#" + id).getStyleClass().add("mapped");
            message.setText(id + " key mapped to " + file.getName() + ".");
        } else {
            if (maps[myMap] != null) {
                maps[myMap].remove(KeyCode.valueOf(id));
                message.getScene().lookup("#" + id).getStyleClass().remove("mapped");
                message.setText(id + " key unmapped.");
            }
        }
    }

    private void switchMap(int newMap) {
        if(newMap != myMap) {
            if(maps[myMap] != null) {
                for (KeyCode k : maps[myMap].keySet()) {
                    message.getScene().lookup("#" + k.getName()).getStyleClass().remove("mapped");
                }
            }
            message.getScene().lookup("#" + myMap).getStyleClass().remove("pressed");

            myMap = newMap;

            if(maps[myMap] != null) {
                for (KeyCode k : maps[myMap].keySet()) {
                    message.getScene().lookup("#" + k.getName()).getStyleClass().add("mapped");
                }
            }
            message.getScene().lookup("#" + myMap).getStyleClass().add("pressed");

            message.setText("Switched to map #" + myMap + ".");
        }
    }

    @FXML protected void saveKeymap(ActionEvent event) {
        try {
            File file = new File("keymap.ser");
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream s = new ObjectOutputStream(f);
            for(Map<KeyCode, Sample> m : maps) {
                s.writeObject((HashMap) m);
            }
            s.writeObject((TreeSet) importedSounds);
            s.close();
            f.close();
            message.setText("Keymap saved.");
        } catch (IOException err) {
            message.setText("Could not save keymap.");
            err.printStackTrace();
        }

        event.consume();
    }

    @FXML protected void importKeymap(ActionEvent event) {
        try {
            File file = new File("keymap.ser");
            FileInputStream f = new FileInputStream(file);
            ObjectInputStream s = new ObjectInputStream(f);
            if(maps[myMap] != null) {
                for (KeyCode k : maps[myMap].keySet()) {
                    message.getScene().lookup("#" + k.getName()).getStyleClass().remove("mapped");
                }
            }
            maps = new Map[10];
            for (int i = 0; i < maps.length; i++) {
                maps[i] = (HashMap<KeyCode, Sample>) s.readObject();
            }
            importedSounds = (TreeSet) s.readObject();
            s.close();
            f.close();

            if(maps[myMap] != null) {
                for (KeyCode k : maps[myMap].keySet()) {
                    message.getScene().lookup("#" + k.getName()).getStyleClass().add("mapped");
                }
            }
            message.setText("Keymap loaded.");
        } catch (IOException|ClassNotFoundException err) {
            message.setText("Could not load keymap.");
        }

        event.consume();
    }
}
