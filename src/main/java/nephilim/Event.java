package nephilim;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


class Event extends Task {
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private final DateTimeFormatter OUTPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");
    private final DateTimeFormatter INPUT_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");


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
