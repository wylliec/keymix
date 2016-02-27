package keymix;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.File;

/**
 * Dropdown menu for all different files user can select from.
 * User should be able to type into menu and see only files that begin with the input text:
 *      NOT case-sensitive.
 *      If multiple options exist, user should be able to use arrow keys to shift between options.
 * Optimization:
 *      Autocomplete functionality.
 */

public class Dropdown extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    final Button button = new Button("Select");
    final Label notification = new Label();
    final ExtensionChecker checker = new ExtensionChecker();

    String address = " ";

    private void addFile(String filepath, ComboBox cb, ExtensionChecker ec) {
        File file = new File(filepath);
        String fileName = file.getName();
        if (ec.checkExtension(fileName)) {
            cb.getItems().addAll(fileName);
        } else {
            System.out.println("Invalid file!");
        }
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("File Selector");
        Scene scene = new Scene(new Group(), 450, 250);

        final ComboBox fileComboBox = new ComboBox();

//        /* Adds sequence of comma separated strings containing the names of all the files
//         * the user can select from.
//         */
//        fileComboBox.getItems().addAll(
//            "j.java" // TODO: CHANGE GENERIC FILE NAME - ADD VALID FILES
//        );

        String filepath = "j.java";

        addFile(filepath, fileComboBox, checker);

        fileComboBox.setPromptText("File name");
        fileComboBox.setEditable(true);
        fileComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                address = t1;
            }
        });

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (fileComboBox.getValue() != null &&
                        !fileComboBox.getValue().toString().isEmpty()){
                    notification.setText("File selected: " + fileComboBox.getValue());
                    fileComboBox.setValue(null);
                }
                else {
                    notification.setText("You have not selected a file!");
                }
            }
        });

        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(new Label("File: "), 0, 0);
        grid.add(fileComboBox, 1, 0);
        grid.add(button, 0, 3);
        grid.add (notification, 1, 3, 3, 1);

        Group root = (Group)scene.getRoot();
        root.getChildren().add(grid);
        stage.setScene(scene);
        stage.show();

    }
}

