import java.awt.*;
import javafx.scene.control.Button;

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

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }
}
