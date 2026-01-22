public class Event extends Task {
    private String fromDate;
    private String toDate;

    public Event(String taskName) {
        super(taskName.substring(0, taskName.indexOf("/from ")));
        this.fromDate = taskName.substring(taskName.indexOf("/from ") + 6,
                                           taskName.indexOf("/to "));
        this.toDate = taskName.substring(taskName.indexOf("/to ") + 3);
    }


    @Override
    public String toString() {
        return "[E]" + super.toString()
                + "(from: " + fromDate
                + " to: " + toDate + ")";
    }
}
