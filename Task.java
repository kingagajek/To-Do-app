public class Task {
    private String name;
    private String description;
    private boolean isCompleted;

    public Task (String name, String description) {
        this.name = name;
        this.description = description;
        this.isCompleted = false;
    }

    public String getName(){
        return name;
    }

    public void setName() {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription() {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted() {
        this.isCompleted = isCompleted;
    }

    public void markAsCompleted() {
        this.isCompleted = true;
    }
}
