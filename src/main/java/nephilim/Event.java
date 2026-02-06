package nephilim;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event - a task that starts on a given timing and ends on a given timing
 * A Deadline object corresponds to a task represented by a task name, a date and time it starts on, and
 * a date and time it ends on.
 */
class Event extends Task {
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private final DateTimeFormatter OUTPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
    private final DateTimeFormatter INPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");


    /**
     * Constructs an Event with three strings.
     *
     * @param taskName String denoting name of the event task
     * @param fromDate String denoting when the event task begins in YYYY-MM-DD TTTT format
     * @param toDate  String denoting when the event task ends in YYYY-MM-DD TTTT format
     * @throws NephilimIOMissingArgsException If the string for byDate OR toDate is not formatted correctly.
     */
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

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + fromDate.format(OUTPUT_DATE_FORMAT)
                + " to: " + toDate.format(OUTPUT_DATE_FORMAT) + ")";
    }

    @Override
    public String encode() {
        return " event " + super.encode() + " /from " + fromDate.format(INPUT_DATE_FORMAT)
                + " /to " + toDate.format(INPUT_DATE_FORMAT);
    }
}
