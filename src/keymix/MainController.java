package keymix;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Jessie on 2/28/2016.
 */
public class MainController {
    @FXML KeymixAppController keymixAppController = new KeymixAppController();
    @FXML SoundManagerController soundManagerController = new SoundManagerController();

    @FXML
    public void initialize() {
        System.out.println("main application started");

//        MainController.getInstance().registerKeymixController();

        keymixAppController.init(this);
        soundManagerController.init(this);
    }

    void registerKeymixController(KeymixAppController keymixApp) {
        this.keymixAppController = keymixApp;
    }

    void registerSoundManagerController(SoundManagerController managerController) {
        this.soundManagerController = managerController;
    }

    // loads imported sounds (soundFilesMap) from SoundManager
    public HashMap<String, String> loadImportedSoundsFromSoundManager() {
        return soundManagerController.getSoundFilesMap();
    }

    public void setImportedSoundsForKeymixApp(HashMap<String, String> hm) {
        keymixAppController.setImportedSounds(hm);
    }

    public void setImportedSoundsForSoundManager(HashMap<String, String> hm) {
        soundManagerController.setSoundFilesMap(hm);
    }

    public void handleBeginButton(ActionEvent event) {
        Parent root;
        try {
            System.out.println("Begin button clicked");

            // sets up and displays SoundManager window
            root = FXMLLoader.load(getClass().getResource("app.fxml"));

            Stage stage = new Stage();
            stage.setTitle("keymix.");
            stage.setScene(new Scene(root, 640, 400));
            stage.show();

            // closes current window
            ((Stage) ((Node) (event.getSource())).getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//
//
//    // support Singleton pattern for MainController
//    private MainController() {}
//
//    public static MainController getInstance() {
//        return MainControllerHolder.INSTANCE;
//    }
//
//    private static class MainControllerHolder {
//        private static final MainController INSTANCE = new MainController();
//    }

}
