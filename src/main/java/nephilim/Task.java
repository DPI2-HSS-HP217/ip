package nephilim;

class Task {
    private boolean isDone;
    private String taskName;

    public Task (String taskName) {
        this.taskName = taskName;
        isDone = false;
    }

    public String getTaskName() {
        return this.taskName;
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
