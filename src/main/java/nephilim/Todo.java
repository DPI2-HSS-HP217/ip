package nephilim;

/**
 * Represents a task that is to be completed.
 */

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
