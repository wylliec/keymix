package keymix;

import java.util.HashMap;

/**
 * Created by Jessie on 2/28/2016.
 */
public class ImportedSoundManager {
    private static ImportedSoundManager instance;
    private HashMap<String, String> importedSounds = new HashMap<>();

    public static ImportedSoundManager getInstance() {
        if (instance == null) {
            instance = new ImportedSoundManager();
        }
        return instance;
    }

    public void put(String fileName, String filePath) {
        importedSounds.put(fileName, filePath);
    }

    public String get(String fileName) {
        return importedSounds.get(fileName);
    }

    public HashMap<String, String> getMap() {
        return importedSounds;
    }

    public void setMap(HashMap map) {
        this.importedSounds = map;
    }
}
