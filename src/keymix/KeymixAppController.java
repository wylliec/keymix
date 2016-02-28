package keymix;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Import;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class KeymixAppController {
    private MainController main;
    private ImportedSoundManager importedSoundManager = ImportedSoundManager.getInstance();
    //@FXML private Window soundManager;
    //@FXML private SoundManagerController soundManagerController;

    // TODO: figure out how to make SoundManagerController and Controller communicate
    private Map<KeyCode, Sample>[] maps = new Map[10];
    private int myMap = 0;

    private final String DEFAULT_OPTION = "Select an option.";

    @FXML private Text message;

    @FXML
    public void initialize() {
        System.out.println("Keymix application started");
        System.out.println(importedSoundManager.getMap());
    }

    public void init(MainController mainController) {
        main = mainController;
    }

    @FXML
    protected void keyPress(KeyEvent keyEvent) {
        String id = keyEvent.getText().toUpperCase();

        try {
            int mapNum = Integer.parseInt(id);
            if(mapNum >=0 && mapNum < 10) {
                message.getScene().lookup("#" + myMap).getStyleClass().remove("pressed");
                myMap = mapNum;
                message.getScene().lookup("#" + myMap).getStyleClass().add("pressed");
            }
        } catch(NumberFormatException nfe) { }

        if (maps[myMap] != null && (keyEvent.getCode() == KeyCode.BACK_SPACE ||
                keyEvent.getCode() == KeyCode.SPACE)) {
            maps[myMap].values().stream().forEach(sample -> sample.getClip().stop());
        }

        if(maps[myMap] != null && id.length() == 1 && Character.isLetter(id.charAt(0))
                && maps[myMap].containsKey(keyEvent.getCode()) && !maps[myMap].get(keyEvent.getCode()).isPlaying()) {
            maps[myMap].get(keyEvent.getCode()).setPlaying(true);
            message.getScene().lookup("#" + id).getStyleClass().add("pressed");
            maps[myMap].get(keyEvent.getCode()).getClip().play();
        }
        keyEvent.consume();
    }

    @FXML
    protected void keyRelease(KeyEvent keyEvent) {
        //String id = keyEvent.getText();
        String id = keyEvent.getText().toUpperCase();
        //System.out.println(id);
        if(maps[myMap] != null && id.length() == 1 && Character.isLetter(id.charAt(0))
                && maps[myMap].containsKey(keyEvent.getCode())) {
            maps[myMap].get(keyEvent.getCode()).setPlaying(false);
            message.getScene().lookup("#" + id).getStyleClass().remove("pressed");
        }
        keyEvent.consume();
    }

    @FXML
    protected void openSoundManager(ActionEvent event) {
        try {
            System.out.println("Open Sound Manager button clicked");

            // sets up and displays SoundManager window
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("soundManager.fxml"));
            Parent root = fxmlLoader.load();

            //main.setImportedSoundsForSoundManager(importedSounds);
            SoundManagerController managerController = fxmlLoader.<SoundManagerController> getController();
            managerController.setSoundFilesMap(importedSoundManager.getMap());

            // sets parent window and controller so it can be referred to later
            Scene currScene = ((Node)(event.getSource())).getScene();
            managerController.setParentController(this);

            Stage stage = new Stage();
            stage.setTitle("keymix . sound manager");
            stage.setScene(new Scene(root, 640, 400));
            stage.show();

            // hides current window
            currScene.getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void clickNumber(ActionEvent event) {
        message.getScene().lookup("#" + myMap).getStyleClass().remove("pressed");
        String id = ((Node)event.getTarget()).idProperty().getValue();
        myMap = id.charAt(0) - '0';
        message.getScene().lookup("#" + myMap).getStyleClass().add("pressed");
        event.consume();
    }

    @FXML
    protected void clickLetter(ActionEvent event) {
        String id = ((Node)event.getTarget()).idProperty().getValue();

        ChoiceDialog<String> dialog = new ChoiceDialog<>(DEFAULT_OPTION, importedSoundManager.getMap().keySet());
        dialog.setTitle("Choose Sample");
        dialog.setHeaderText("Choose what sample you would like to map to letter " + id);
        dialog.setContentText("Sample:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> this.mapKey(name, id));

        event.consume();
    }

    private void mapKey(String name, String id) {
        if (!name.equals(DEFAULT_OPTION)) {
            if (maps[myMap] == null) {
                maps[myMap] = new HashMap<>();
            }
            File f = new File(importedSoundManager.get(name));
            maps[myMap].put(KeyCode.valueOf(id), new Sample(f.toURI().toString()));
        } else {
            if (maps[myMap] != null) {
                maps[myMap].remove(KeyCode.valueOf(id));
            }
        }
    }

    @FXML
    protected void saveKeymap(ActionEvent event) {
        try {
            File file = new File("keymap.ser");
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream s = new ObjectOutputStream(f);
            for(Map<KeyCode, Sample> m : maps) {
                s.writeObject((HashMap) m);
            }
            s.writeObject((HashMap) importedSoundManager.getMap());
            s.close();
            f.close();
            message.setText("Keymap saved.");
        } catch (IOException err) {
            message.setText("Could not save keymap.");
            err.printStackTrace();
        }

        event.consume();
    }

    @FXML
    protected void importKeymap(ActionEvent event) {
        try {
            File file = new File("keymap.ser");
            FileInputStream f = new FileInputStream(file);
            ObjectInputStream s = new ObjectInputStream(f);
            maps = new Map[10];
            for (int i = 0; i < maps.length; i++) {
                maps[i] = (HashMap<KeyCode, Sample>) s.readObject();
            }
            importedSoundManager.setMap((HashMap) s.readObject());
            s.close();
            f.close();
            message.setText("Keymap loaded.");
        } catch (IOException|ClassNotFoundException err) {
            message.setText("Could not load keymap.");
        }

        event.consume();
    }

    public void setImportedSounds(HashMap<String, String> hm) {
        importedSoundManager.setMap(hm);
    }
}
