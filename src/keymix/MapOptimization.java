package keymix;

import java.util.Scanner;
import java.util.HashMap;
import javafx.scene.input.KeyCode;
import java.util.ArrayList;

/**
 * Allows for optimized user-input based HashMap features:
 *      User can bind numerical hotkeys to different Maps.
 *      User can assign names to different Maps.
 */
public class MapOptimization {

    /* Index to be used in assignName() to track how many previous times a name has been assigned to a Map. */
    int index = 0;

    /* KeyCodes for all valid keys. */
    KeyCode[] codes = new KeyCode[]{KeyCode.A, KeyCode.B, KeyCode.C, KeyCode.D, KeyCode.E, KeyCode.F, KeyCode.G,
            KeyCode.H, KeyCode.I, KeyCode.J, KeyCode.K, KeyCode.L, KeyCode.M, KeyCode.N, KeyCode.O, KeyCode.P,
            KeyCode.Q, KeyCode.R, KeyCode.S, KeyCode.T, KeyCode.U, KeyCode.V, KeyCode.W, KeyCode.X, KeyCode.Y, KeyCode.Z,
            KeyCode.DIGIT0, KeyCode.DIGIT1, KeyCode.DIGIT2, KeyCode.DIGIT3, KeyCode.DIGIT4, KeyCode.DIGIT5,
            KeyCode.DIGIT6, KeyCode.DIGIT7, KeyCode.DIGIT8, KeyCode.DIGIT9,
            KeyCode.SPACE, KeyCode.SHIFT, KeyCode.CONTROL, KeyCode.BACK_SPACE};

    /* Filepaths for all sound bytes to be available.
     * First permutation.
     */
    String[] filepaths = new String[]{
            // Insert filepaths as Strings (JESSIE)
    };

    /* Filepaths for all sound bytes to be available.
     * Second permutation.
     */
    String[] filepathsTwo = new String[]{
            // Insert filepaths as Strings (JESSIE)
    };

    /* Filepaths for all sound bytes to be available.
     * Third permutation.
     */
    String[] filepathsThree = new String[]{
            // Insert filepaths as Strings (JESSIE)
    };

    /* Array containing all HashMaps that will be used to map keys to different sounds.
     * Current minimum number of permutations allowed is three.
     */
    HashMap[] maps = new HashMap[]{
            SoundMap.map(codes, filepaths, new HashMap<KeyCode, String>()),
            SoundMap.map(codes, filepathsTwo, new HashMap<KeyCode, String>()),
            SoundMap.map(codes, filepathsThree, new HashMap<KeyCode, String>())
    };

    /* ArrayList of all keys that have been toggled by the user. */
    ArrayList<Integer> toggledKeys = new ArrayList<Integer>();

    /* Allows user to assign a numerical key to a HashMap.
     * Key input by user must be integer between 0 and length of array containing HashMaps (exclusive).
     * Returns HashMap pointing to the toggled HashMap in array (see SoundMap - optimize()).
     */
    public HashMap<KeyCode, String> assignHotKey() {
        Scanner key_input = new Scanner(System.in);

        System.out.print("Enter numerical hotkey: ");
        int key = key_input.nextInt();

        if (key < maps.length) {
            toggledKeys.add(key);
            return maps[key];
        } else {
            System.out.println("Invalid input!");
            System.out.println("Key must be less than " + maps.length + " and greater than 0");
            return null;
        }
    }

    /* Allows user to enter in a hotkey.
     * If key has been bound to a HashMap, that Map is set as the map of sound bite permutations to be used
     * on the keyboard.
     * If key has not already been bound, send that warning to user.
     *
     * ************ NEEDS WORK. ************
     */
    public void toggleKey() {
        Scanner key_input = new Scanner(System.in);

        System.out.print("Enter numerical hotkey: ");
        int key = key_input.nextInt();

        if (toggledKeys.contains(key)) {
            HashMap<KeyCode, String> toUse = maps[key];
        } else if (key > maps.length || key == 0){
            System.out.println("Key must be less than " + maps.length + " and greater than 0");
        } else {
            System.out.println("That key has not yet been bound.");
        }
    }

    /* Allows user to re-assign name of different HashMaps.
     * User enters in desired name and name is returned.
     *
     * Desired algorithm:
     *      Obtain desired HashMap name.
     *      Set input name as name of separate HashMap.
     *      Have aforementioned Map point to same map to be implemented in the keyboard.
     *
     * ************ MIGHT NEED WORK. ************
     * ********* ((MIGHT BE CORRECT)) ***********
     */
    public void assignName() {
        if (index >= maps.length) {
            System.out.println("Exceeded SoundMap renaming limit.");
            return;
        }

        Scanner user_input = new Scanner(System.in);
        HashMap<String, HashMap<KeyCode, String>> renamed = new HashMap<>();

        String name;
        System.out.print("Enter name for KeyMap: ");
        name = user_input.next();

        renamed.put(name, maps[index]);
        index += 1;
    }
}
