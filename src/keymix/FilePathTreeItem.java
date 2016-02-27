package keymix;

import javafx.scene.control.TreeItem;
//import javafx.scene.shape.Path;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;


/**
 * Created by Jessie on 2/27/2016.
 *
 * Partial code by  Hugues Johnson, found at https://dzone.com/articles/writing-simple-file-browser
 *
 * Probably unnecessary
 */
public class FilePathTreeItem extends TreeItem<String> {

    // stores full path to file or directory
    private String fullPath;
    private boolean isDirectory;

    public FilePathTreeItem(Path file) {
        super(file.toString());
        this.fullPath = file.toString();

        // tests if path leads to directory, sets icon???
        if (Files.isDirectory(file)) {
            this.isDirectory = true;
            // TODO: set graphic
        } else {
            this.isDirectory = false;
            // TODO: set graphic
        }

        // sets value
        if (!fullPath.endsWith(File.separator)) {
            // sets value, which is what's displayed in tree
            String value = file.toString();
            int indexOf = value.lastIndexOf(File.separator);

            if (indexOf > 0) {
                this.setValue(value.substring(indexOf + 1));
            } else {
                this.setValue(value);
            }
        }

        // adds event handler for when the node is expanded
        // overrides handle method in EventHandler
        this.addEventHandler(TreeItem.branchExpandedEvent(), (e) -> {
            FilePathTreeItem source = (FilePathTreeItem) (Object) e.getSource();

            if (source.isDirectory() && source.isExpanded()) {
                // TODO: set graphic
                // ImageView iv = (ImageView) source.getGraphic();
                // iv.setImage(folderExpandImage);
            }

            try {
                // if there are no children
                if (source.getChildren().isEmpty()) {
                    Path path = Paths.get(source.getFullPath());
                    BasicFileAttributes attributes = Files.readAttributes(path, BasicFileAttributes.class);

                    // dynamically fetches all the files and subdirectories of this directory
                    // and displays them
                    if (attributes.isDirectory()) {
                        DirectoryStream<Path> dir = Files.newDirectoryStream(path);

                        // adds each file/directory as new FilePathTreeItem
                        for (Path f : dir) {
                            FilePathTreeItem treeNode = new FilePathTreeItem(f);
                            source.getChildren().add(treeNode);
                        }
                    }
                } else {
                    // TODO: implement rescanning of directory for changes
                }
            } catch (IOException x) {
                x.printStackTrace();
            }
        });

        // adds event handler for when the node is collapsed
        // overrides handle method in EventHandler
        this.addEventHandler(TreeItem.branchCollapsedEvent(), (e) -> {
            FilePathTreeItem source = (FilePathTreeItem) (Object) e.getSource();

            if (source.isDirectory() && !source.isExpanded()) {
                // TODO: set graphic
                // ImageView iv = (ImageView) source.getGraphic();
                // iv.setImage(folderCollapseImage);
            }
        });
    }


    // ---------------- GETTERS -------------
    public String getFullPath() {
        return this.fullPath;
    }

    public boolean isDirectory() {
        return isDirectory;
    }


}
