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
        assert (!taskName.isEmpty());
        try {
            this.fromDate = DateTimeParser.parseTime(fromDate);
            this.toDate = DateTimeParser.parseTime(toDate);
        } catch (NephilimIOMissingArgsException e) {
            throw new NephilimIOMissingArgsException("event " + taskName, e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " +  DateTimeParser.toOutputString(fromDate)
                + " to: " +  DateTimeParser.toOutputString(toDate) + ")";
    }

    @Override
    public String encode() {
        return " event " + super.encode() + " /from " +  DateTimeParser.toInternalString(fromDate)
                + " /to " +  DateTimeParser.toInternalString(toDate);
    }

    /**
     *
     * @param date The date to check if the Task is on the schedule for.
     * @return If the event has started on the given date.
     */
    @Override
    public boolean isOnDate(LocalDateTime date) {
        boolean hasStarted = !(this.fromDate.isAfter(date));
        boolean hasNotEnded = this.toDate.isAfter(date);
        return (hasStarted && hasNotEnded);
    }

}
