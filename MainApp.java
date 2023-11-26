import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Test connection
        try (Connection conn = DatabaseHandler.getConnection()) {
            System.out.println("Połączono z bazą danych SQLite.");
            // Tutaj można wykonać operacje na bazie danych
        } catch (SQLException e) {
            System.out.println("Nie udało się połączyć z bazą danych: " + e.getMessage());
        }
        initDatabase();


        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 700, 650);

        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

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
                            Text text = new Text(item.getTaskDescription());
                            TextField editTextField = new TextField(item.getTaskDescription());
                            editTextField.setVisible(false); // początkowo pole edycji jest ukryte

                            CheckBox checkBox = new CheckBox();
                            checkBox.setSelected(item.isCompleted());

                            updateLabelStyle(text, item.isCompleted());
                            checkBox.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    boolean isSelected = checkBox.isSelected();
                                    item.setCompleted(isSelected);
                                    updateLabelStyle(text, isSelected);
                                    listView.refresh();
                                }
                            });

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
                                    text.setVisible(false);
                                    editTextField.setVisible(true);
                                    editTextField.requestFocus();
                                }
                            });

                            editTextField.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    item.setTaskDescription(editTextField.getText());
                                    text.setText(item.getTaskDescription());
                                    editTextField.setVisible(false);
                                    text.setVisible(true);
                                    listView.refresh();
                                }
                            });

                            HBox hBox = new HBox(text, editTextField, checkBox, deleteButton, editButton); // Umieść label i przycisk w HBox
                            hBox.setSpacing(10); // przestrzen między label a przyciskiem
                            setGraphic(hBox); // HBox jako zawartość graficzną komórki
                        }
                    }
                    private void updateLabelStyle(Text text, boolean isCompleted) {
                        text.getStyleClass().clear();
                        text.getStyleClass().add(isCompleted ? "text-completed" : "text-active");
                    }
                };
            }
        });

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TaskItem taskItem = new TaskItem(textField.getText());
                listView.getItems().add(taskItem);
                DatabaseHandler.addTask(taskItem);
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

    private void initDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS tasks ("
                + " id integer PRIMARY KEY,"
                + " description text NOT NULL,"
                + " completed boolean NOT NULL CHECK (completed IN (0,1))"
                + ");";

        try (Connection conn = DatabaseHandler.getConnection();
             Statement stmt = conn.createStatement()) {
            // Tworzenie tabeli w bazie danych
            stmt.execute(createTableSQL);
            System.out.println("Tabela została utworzona.");
        } catch (SQLException e) {
            System.out.println("Błąd podczas tworzenia tabeli: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}