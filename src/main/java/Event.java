import java.util.StringTokenizer;

public class Event extends Task {
    private String fromDate;
    private String toDate;

    public Event(String taskName) throws NephilimInputException {
        super(taskName);

        //double condition because from field being empty and from tag missing
        //require separate handling.
        if (!taskName.contains("/from ") ||
             taskName.substring(taskName.indexOf("/from ")).length() <= 6) {
            throw new NephilimInputException("event " + taskName, "From field (denoted by \"/from\" flag) is either" +
                                             " empty or the \"/from\" flag is missing.");
        } else if (!taskName.contains("/to ")) {
            throw new NephilimInputException("event " + taskName, "To field (denoted after \"/by\") is either" +
                                             " empty or the \"/to\" flag is missing.");
        } else if (taskName.indexOf("/from") == 1) {
            throw new NephilimInputException("event" + taskName, "Event name cannot be empty.");
        }
        super.setTaskName(taskName.substring(0, taskName.indexOf("/from")));
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
