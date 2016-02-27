package keymix;

import javafx.scene.input.KeyCode;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class KeysPressed {

    List<KeyCode> pressed = new ArrayList<KeyCode>();

    public KeysPressed(HashMap<KeyCode, Boolean> keys) {
    /*  Parameters:
            HashMap<KeyCode, boolean> : a HashMap of all KeyCodes
                we will use in the UI, with booleans corresponding
                to if those keys were pressed in the last 100 ms.
     */
        updateKeys(keys);

    }

    private void updateKeys(HashMap<KeyCode, Boolean> keys) {
    /*  Adds KeyCodes of all keys which were pressed in the
        last 100 ms to List<KeyCodes> pressed.
     */
        for (KeyCode key: keys.keySet()) {
            boolean value = keys.get(key);
            if (value) {
                pressed.add(key);
            }
        }
    }

    public List<KeyCode> keys() {
    /*  Returns pressed, which is a List of all keys which
        have been pressed in the last 100 ms.
    */
        return pressed;
    }

}
