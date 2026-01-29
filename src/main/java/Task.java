public class Task {
    private boolean isDone;
    private String taskName;

    public Task (String taskName) {
        this.taskName = taskName;
        isDone = false;
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    public void setDone() {
        this.isDone = true;
    }

    public void setUnDone() {
        this.isDone = false;
    }

    public void setTaskName(String newName) {
        this.taskName = newName;
    }

    public String encode() {
        return taskName;
    }




    @Override
    public String toString() {
        String status = this.isDone ? "X" : " ";
        String out = "[" + status + "] " + taskName;
        return out;
    }


}
