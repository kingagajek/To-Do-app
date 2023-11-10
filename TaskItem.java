import java.awt.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class TaskItem {
    private String taskDescription;
    private Button deleteButton;

    public TaskItem(String taskDescription) {
        this.taskDescription = taskDescription;
        this.deleteButton = new Button("Usuń");
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription() {
        this.taskDescription = taskDescription;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }
}
