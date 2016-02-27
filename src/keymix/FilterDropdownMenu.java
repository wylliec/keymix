package keymix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.Group;

/**
 * Provides functionality to filter through a dropdown menu upon user input into search bar.
 */
public class FilterDropdownMenu extends Application {

    final Button button = new Button("Select");
    final Label notification = new Label();

    String address = " ";

    @Override
    public void start(Stage primaryStage) {

        /* List contains all files to be selected from. */
        final List<String> items = Arrays.asList(new String[] {
                // Strings of file names.
        });

        final ComboBox<String> comboBox = new ComboBox<>(
                FXCollections.observableArrayList(items));

        primaryStage.setTitle("File Selector");

        comboBox.setEditable(true);

        comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                address = t1;
            }
        });

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (comboBox.getValue() != null &&
                        !comboBox.getValue().toString().isEmpty()){
                    notification.setText("File selected: " + comboBox.getValue());
                    comboBox.setValue(null);
                }
                else {
                    notification.setText("You have not selected a file!");
                }
            }
        });

        comboBox.getEditor().textProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, String newValue) {
                        final TextField editor = comboBox.getEditor();
                        final String selected = comboBox.getSelectionModel()
                                .getSelectedItem();
                        if (selected == null || !selected.equals(editor.getText())) {
                            filterItems(newValue, comboBox, items);
                            comboBox.show();
                            if (comboBox.getItems().size() == 1) {
                                final String onlyOption = comboBox.getItems().get(0);
                                final String current = editor.getText();
                                if (onlyOption.length() > current.length()) {
                                    editor.setText(onlyOption);
                                    // This only works using Platform.runLater(...)
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            editor.selectRange(current.length(), onlyOption.length());
                                        }
                                    });
                                }
                            }
                        }
                    }
                });
/**************************************************************************************
 *  The commented-out code is an attempt to revert to the full list on a selection being made.
 *  It seems to make this work we need to delay the processing until after the default handlers have been invoked;
 *  hence the Platform.runLater(...).
 *  May not be desirable from a usability standpoint anyway.
 *  Scrolling is affected with this enabled; if you type and then use the arrow keys for selection, it will not
 *  scroll to the selected item on the first press of the arrow keys.

//    comboBox.setOnAction(new EventHandler<ActionEvent>() {
//      @Override
//      public void handle(ActionEvent event) {
//        // Reset so all options are available:
//        Platform.runLater(new Runnable() {
//          @Override
//          public void run() {
//            String selected = comboBox.getSelectionModel().getSelectedItem();
//            if (comboBox.getItems().size() < items.size()) {
//              comboBox.setItems(FXCollections.observableArrayList(items));
//              String newSelected = comboBox.getSelectionModel()
//                  .getSelectedItem();
//              if (newSelected == null || !newSelected.equals(selected)) {
//                comboBox.getSelectionModel().select(selected);
//              }
//            }
//          }
//        });
//      }
//    });

**************************************************************************************/

        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        grid.add(comboBox, 1, 0);
        grid.add(button, 0, 3);
        grid.add (notification, 1, 3, 3, 1);

        primaryStage.setTitle("File Selector");

        Scene scene = new Scene(new Group(), 450, 250);

        Group root = (Group)scene.getRoot();
        root.getChildren().add(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private <T> void filterItems(String filter, ComboBox<T> comboBox,
                                 List<T> items) {
        List<T> filteredItems = new ArrayList<>();
        for (T item : items) {
            if (item.toString().toLowerCase().startsWith(filter.toLowerCase())) {
                filteredItems.add(item);
            }
        }
        comboBox.setItems(FXCollections.observableArrayList(filteredItems));
    }

    public static void main(String[] args) {
        launch(args);
    }
}