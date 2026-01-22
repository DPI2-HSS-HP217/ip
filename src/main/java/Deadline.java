public class Deadline extends Task {
    private String bydate;

    public Deadline(String taskName) {
        super(taskName.substring(0, taskName.indexOf("/by ")));
        this.bydate = taskName.substring(taskName.indexOf("/by ") + 4);
    }


    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by: " + bydate + ")";
    }
}
