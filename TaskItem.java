import java.awt.*;
import javafx.scene.control.Button;

public class TaskItem {
    private String taskDescription;
    private boolean completed;

    public TaskItem(String taskDescription) {
        this.taskDescription = taskDescription;
       // this.completed = false;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

   // public void markAsCompleted() {
     //   this.completed = true;
    //}

}
