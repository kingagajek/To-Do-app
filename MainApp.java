import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 700, 650);

        TextField textField = new TextField();
        Button addButton = new Button("Dodaj");
        ListView<TaskItem> listView = new ListView<>();

        listView.setCellFactory(new Callback<ListView<TaskItem>, ListCell<TaskItem>>() {
            @Override
            public ListCell<TaskItem> call(ListView<TaskItem> listView) {
                return new ListCell<TaskItem>() {
                    @Override
                    protected void updateItem(TaskItem item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null); // Jeśli komórka jest pusta, nie pokazuj niczego
                            setGraphic(null); // Nie pokazuj żadnych grafik w komórce
                        } else {
                            Label label = new Label(item.getTaskDescription()); // Stwórz nowy label z opisem zadania
                            TextField editTextField = new TextField(item.getTaskDescription());
                            editTextField.setVisible(false); // początkowo pole edycji jest ukryte
                            Button deleteButton = new Button("Usuń"); // Stwórz przycisk do usuwania zadania
                            deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                                 @Override
                                 public void handle(ActionEvent event) {
                                     listView.getItems().remove(item);
                                 }
                            });
                            Button editButton = new Button("Edytuj");
                            editButton.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    label.setVisible(false);
                                    editTextField.setVisible(true);
                                    editTextField.requestFocus();
                                }
                            });

                            editTextField.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    item.setTaskDescription(editTextField.getText());
                                    label.setText(item.getTaskDescription());
                                    editTextField.setVisible(false);
                                    label.setVisible(true);
                                    listView.refresh();
                                }
                            });

                            HBox hBox = new HBox(label, editTextField, deleteButton, editButton); // Umieść label i przycisk w HBox
                            hBox.setSpacing(10); // Dodaj trochę przestrzeni między label a przyciskiem
                            setGraphic(hBox); // Ustaw HBox jako zawartość graficzną komórki
                        }
                    }
                };
            }
        });

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TaskItem taskItem = new TaskItem(textField.getText());
                listView.getItems().add(taskItem);
                textField.clear();
            }
        });


        VBox vbox = new VBox(5);
        vbox.getChildren().addAll(textField, addButton, listView);

        root.setCenter(vbox);

        primaryStage.setTitle("Lista ToDo");

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}