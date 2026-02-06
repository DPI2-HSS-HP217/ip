package nephilim;

/**
 * Represents a task that is to be completed.
 */

//NOTE: May be considered redundant compared to just using Task.
class Todo extends Task {
    public Todo (String taskName) {
        super(taskName);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String encode() {
        return " todo " + super.encode();
    }
}
