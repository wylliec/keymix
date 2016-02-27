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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import java.awt.Insets;
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

        final List<String> items = Arrays.asList(new String[] { "Alabama",
                "Alaska", "Arizona", "Arkansas", "California", "Colorado",
                "Connecticut", "Delaware", "Georgia", "Florida", "Hawaii", "Idaho",
                "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana",
                "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota",
                "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada",
                "New Hampshire", "New Jersey", "New Mexico", "New York",
                "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon",
                "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota",
                "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington",
                "West Virginia", "Wisconsin", "Wyoming" });
        // final BorderPane root = new BorderPane();
        final ComboBox<String> comboBox = new ComboBox<>(
                FXCollections.observableArrayList(items));

        primaryStage.setTitle("File Selector");

        comboBox.setEditable(true);

        comboBox.setPromptText("File name");
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
                                    // Not quite sure why this only works using
                                    // Platform.runLater(...)
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

        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(10);
        // grid.setPadding(new Insets(5, 5, 5, 5));
        // grid.add(new Label("File: "), 0, 0);
        grid.add(comboBox, 1, 0);
        grid.add(button, 0, 3);
        grid.add (notification, 1, 3, 3, 1);

        primaryStage.setTitle("File Selector");

        // root.setTop(comboBox);
        // Label label = new Label();
        // label.textProperty().bind(
        //        comboBox.getSelectionModel().selectedItemProperty());
        // root.setBottom(label);
        // Scene scene = new Scene(root, 200, 400);
        Scene scene = new Scene(new Group(), 450, 250);


        // root.getChildren().add(grid);

        // primaryStage.setScene(scene);
        // primaryStage.show();

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