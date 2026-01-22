public class Task {
    private boolean status;
    private String taskName;

    public Task (String taskName) {
        this.taskName = taskName;
        status = false;
    }

    public void setDone() {
        this.status = true;
    }

    public void resetDone() {
        this.status = false;
    }


    @Override
    public String toString() {
        String isDone = status ? "X" : " ";
        String out = "[" + isDone + "] " + taskName;
        return out;
    }

}
