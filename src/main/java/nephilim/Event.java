package nephilim;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


class Event extends Task {
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    public final DateTimeFormatter OUTPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
    public final DateTimeFormatter INPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");


    public Event(String taskName, String fromDate, String toDate) throws NephilimIOMissingArgsException {
        super(taskName);
        try {
            this.fromDate = LocalDateTime.parse(fromDate, INPUT_DATE_FORMAT);
            this.toDate = LocalDateTime.parse(toDate, INPUT_DATE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new NephilimIOMissingArgsException("event " + taskName, "Could not understand the date "
                    + e.getParsedString() + ". Please ensure date is in YYYY-MM-DD TTTT format, with time in 24 Hour Time.");
        }
    }
    /*
    public Event(String taskName) throws NephilimInputException {
        super(taskName);

        //double condition because from field being empty and from tag missing
        //require separate handling.
        if (!taskName.contains("/from ")
                || taskName.substring(taskName.indexOf("/from ")).length() <= 6) {
            throw new NephilimInputException("event " + taskName, "From field (denoted by \"/from\" flag) is either"
                    + " empty or the \"/from\" flag is missing.");
        } else if (!taskName.contains("/to ")) {
            throw new NephilimInputException("event " + taskName, "To field (denoted after \"/by\") is either"
                    + " empty or the \"/to\" flag is missing.");
        } else if (taskName.indexOf("/from") == 1) {
            throw new NephilimInputException("event" + taskName, "Event name cannot be empty.");
        }
        super.setTaskName(taskName.substring(0, taskName.indexOf("/from")));
        try {
            this.fromDate = LocalDateTime.parse(taskName.substring(taskName.indexOf("/from ") + 6,
                    taskName.indexOf("/to ") - 1), INPUT_DATE_FORMAT);
            this.toDate = LocalDateTime.parse(taskName.substring(taskName.indexOf("/to ") + 4),
                    INPUT_DATE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new NephilimInputException("event " + taskName, "Could not understand the date "
                    + e.getParsedString() + ". Please ensure date is in YYYY-MM-DD TTTT format, with time in 24 Hour Time.");
        }
    }

     */

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(from: " + fromDate.format(OUTPUT_DATE_FORMAT)
                + " to: " + toDate.format(OUTPUT_DATE_FORMAT) + ")";
    }

    @Override
    public String encode() {
        return " event " + super.encode() + " /from " + fromDate.format(INPUT_DATE_FORMAT)
                + " /to " + toDate.format(INPUT_DATE_FORMAT);
    }
}
