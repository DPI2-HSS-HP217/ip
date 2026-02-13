package nephilim;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline. A Deadline object corresponds
 * to a task represented by a task name and a date it is to be completed by.
 */
class Deadline extends Task {
    private LocalDateTime byDate;
    private final DateTimeFormatter OUTPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
    private final DateTimeFormatter INPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

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
            this.byDate = LocalDateTime.parse(byDate, INPUT_DATE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new NephilimIOMissingArgsException("event " + taskName, "Could not understand the date "
                    + e.getParsedString() + ". Please ensure date is in YYYY-MM-DD TTTT format, with time in 24 Hour Time.");
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + byDate.format(OUTPUT_DATE_FORMAT) + ")";
    }

    @Override
    public String encode() {
        return " deadline " + super.encode() + " /by " + byDate.format(INPUT_DATE_FORMAT);
    }
}
