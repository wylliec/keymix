package keymix;

import javafx.scene.input.KeyCode;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class KeyExample implements KeyListener {

    public Key[] keys = new Key[numKeys];
    /*
    14        `1234567890-= back
    14        tab qwertyuiop[]\
    13        caps asdfghjkl;' enter
    12        shift xcvbnm,./ shift
    9         ctrl alt space alt ctrl left right up down
    62

    */

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();

    public KeyExample (Main game) {
        game.addKeyListener(this);
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
//    public Key backtick = new Key();
//
//    public Key q = new Key();
//    public Key w = new Key();
//    public Key e = new Key();
//    public Key r = new Key();
//    public Key t = new Key();
//    public Key y = new Key();
//    public Key u = new Key();
//    public Key i = new Key();
//    public Key o = new Key();
//    public Key p = new Key();
//    public Key a = new Key();
//    public Key s = new Key();
//    public Key d = new Key();
//    public Key f = new Key();
//    public Key g = new Key();
//    public Key h = new Key();
//    public Key j = new Key();
//    public Key k = new Key();
//    public Key l = new Key();
//    public Key z = new Key();
//    public Key x = new Key();
//    public Key c = new Key();
//    public Key v = new Key();
//    public Key b = new Key();
//    public Key n = new Key();
//    public Key m = new Key();
