public class Task {
    private boolean isDone;
    private String taskName;


    public Task (String taskName) {
        this.taskName = taskName;
        isDone = false;
    }

    public void setDone() {
        this.isDone = true;
    }

    public void setTaskName(String newName) {
        this.taskName = newName;
    }

    public void resetDone() {
        this.isDone = false;
    }


    @Override
    public String toString() {
        String status = this.isDone ? "X" : " ";
        String out = "[" + status + "] " + taskName;
        return out;
    }

}
