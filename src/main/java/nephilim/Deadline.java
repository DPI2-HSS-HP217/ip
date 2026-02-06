package nephilim;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

class Deadline extends Task {
    private LocalDateTime byDate;
    public final DateTimeFormatter OUTPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
    public final DateTimeFormatter INPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");


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
        return "[D]" + super.toString() + "(by: " + byDate.format(OUTPUT_DATE_FORMAT) + ")";
    }

    @Override
    public String encode() {
        return " deadline " + super.encode() + " /by " + byDate.format(INPUT_DATE_FORMAT);
    }
}
