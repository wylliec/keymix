package keymix;

import java.util.HashMap;
import javafx.scene.input.KeyCode;
import java.io.File;

/**
 * HashMap to map keycodes to the corresponding sound they should produce.
 */
public class SoundMap extends MapOptimization {

    /* Maps keycodes to string containing the file name of the sound byte. */
    HashMap<KeyCode, String> sounds = new HashMap<KeyCode, String>();

    /* KeyCodes for all valid keys. */
    KeyCode[] codes = new KeyCode[]{KeyCode.A, KeyCode.B, KeyCode.C, KeyCode.D, KeyCode.E, KeyCode.F, KeyCode.G,
            KeyCode.H, KeyCode.I, KeyCode.J, KeyCode.K, KeyCode.L, KeyCode.M, KeyCode.N, KeyCode.O, KeyCode.P,
            KeyCode.Q, KeyCode.R, KeyCode.S, KeyCode.T, KeyCode.U, KeyCode.V, KeyCode.W, KeyCode.X, KeyCode.Y, KeyCode.Z,
            KeyCode.DIGIT0, KeyCode.DIGIT1, KeyCode.DIGIT2, KeyCode.DIGIT3, KeyCode.DIGIT4, KeyCode.DIGIT5,
            KeyCode.DIGIT6, KeyCode.DIGIT7, KeyCode.DIGIT8, KeyCode.DIGIT9,
            KeyCode.SPACE, KeyCode.SHIFT, KeyCode.CONTROL, KeyCode.BACK_SPACE};

    /* Filepaths for all sound bytes to be available. */
    String[] filepaths = new String[]{
        // Insert filepaths as Strings
    };

    /* Given a filepath as a String, return the name of the file. */
    public static String name(String filepath) {
        File f = new File(filepath);
        return f.getName();
    }

    /* Combines array of KeyCodes and array of filepaths into one HashMap. */
    public static HashMap<KeyCode, String> map(KeyCode[] c, String[] fp, HashMap<KeyCode, String> m) {
        for(int i = 0; i < c.length; i += 1) {
            m.put(c[i], name(fp[i]));
        }
        return m;
    }

    /* Allows for optimization of HashMap - key binding and name re-assignment. */
    public void optimize() {
        sounds = assignHotKey();
        assignName();
    }
}
