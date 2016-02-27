package keymix;

import java.util.List;
import java.util.ArrayList;

/**
 * Checks if file extensions are valid extensions.
 * Files are passed in as Strings.
 *
 * Current valid extensions:
 *    1. .mp3
 *    2. .midi
 */
public class ExtensionChecker {
    List<String> validExtensions = new ArrayList<String>();

    public ExtensionChecker() {
        validExtensions.add(".mp3");
        validExtensions.add(".midi");
    }

    public boolean checkExtension(String file) {
        int dotIndex = 0;
        for (int i = 0; i < file.length(); i += 1) {
            if (file.charAt(i) == '.') {
                dotIndex = i;
                break;
            }
        }
        String extension = file.substring(dotIndex, file.length());
        return validExtensions.contains(extension);
    }
}
