package nephilim;

/**
 * Represents a task with a name and a boolean denoting whether the task
 * is considered complete or not.
 */
class Task {
    private boolean isDone;
    private String taskName;

    public Task (String taskName) {
        this.taskName = taskName;
        isDone = false;
    }


    public boolean getIsDone() {
        return this.isDone;
    }

    /**
     * Sets the task to be considered completed.
     */
    public void setDone() {
        this.isDone = true;
    }

    /**
     * Sets task to be considered incomplete.
     */
    public void setUnDone() {
        this.isDone = false;
    }

    /**
     * Returns the task as a string that can be more easily read by
     * the Parser object but not the user. Used for internal data operations.
     * @return Encoded string representing Task.
     */
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
