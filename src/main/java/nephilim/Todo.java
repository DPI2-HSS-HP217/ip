package nephilim;

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
