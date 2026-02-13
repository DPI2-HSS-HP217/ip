package nephilim;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline. A Deadline object corresponds
 * to a task represented by a task name and a date it is to be completed by.
 */
class Deadline extends Task {
    private LocalDateTime byDate;

    /**
     * Constructs a Deadline with two strings.
     *
     * @param taskName String denoting name of the deadline task
     * @param byDate String denoting when the deadline task is due by in YYYY-MM-DD TTTT format
     * @throws NephilimIOMissingArgsException If the string for byDate is not formatted correctly.
     */
    public Deadline (String taskName, String byDate) throws NephilimIOMissingArgsException {
        super(taskName);
        try {
            this.byDate = DateTimeParser.parseTime(byDate);
        } catch (NephilimIOMissingArgsException e) {
            throw new NephilimIOMissingArgsException("deadline " + taskName, e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + DateTimeParser.toOutputString(byDate) + ")";
    }

    @Override
    public String encode() {
        return " deadline " + super.encode() + " /by " +  DateTimeParser.toInternalString(byDate);
    }

    /**
     *
     * @param date The date to check if the Task is on the schedule for.
     * @return If the deadline task has not ended by the given date.
     */
    @Override
    public boolean isOnDate(LocalDateTime date) {
        return this.byDate.isAfter(date);
    }

}


