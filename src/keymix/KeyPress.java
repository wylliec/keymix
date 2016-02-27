package keymix;

import javafx.scene.input.KeyCode;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;


public class KeyPress implements KeyListener {

    /*  KeyPress will recognize "KeyPressed" events. It takes in a
        HashMap {KeyCode: bool}, with KeyCode corresponding to all
        the keys represented on the UI, and updates the bools for
        corresponding KeyEvents for if the key was pressed or not
        in the previous 100 ms.

     */
    List<KeyCode> pressed = new ArrayList<KeyCode>();

    public KeyPress {
        keys();
    }

    public KeyPress(HashMap<KeyCode, boolean> keys) {
        keys
    }

    public void updateKeys() {
        /*  updateKeys() should register the KeyCodes of all the KeyEvents that
            happen in one

         */
    }

    public HashMap<KeyCode, boolean> keys() {
        return keys;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        toggle(e, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggle(e, false);
    }

    @Override
    public KeyCode keyTyped(KeyEvent e) {
        return e.getKeyCode();
    }

    public class Key {
        public boolean down;
        public Key() {
            keys.add(this);
        }
        public void toggle(boolean pressed) {
            if (pressed != down) down = pressed;
        }
    }

}
