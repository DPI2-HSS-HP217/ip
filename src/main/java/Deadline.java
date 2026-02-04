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
    /*
    //to clean up exception error handling by merging into a more concise form where possible
    public Deadline(String taskName) throws NephilimInputException {
        super(taskName); //initializing with wrong name first to get past
        //"super must be first line in constructor" requirement.
        if (!taskName.contains("/by ")) {
            throw new NephilimInputException("deadline " + taskName, "Deadline field (denoted after \"/by\") is "
                    + "either empty or missing the \"/by\" flag");
        } else if (taskName.indexOf("/by") == 1) {
            throw new NephilimInputException("deadline" + taskName, "Deadline task name cannot be empty.");
        }
        super.setTaskName(taskName.substring(0, taskName.indexOf("/by ")));
        try {
            this.byDate = LocalDateTime.parse(taskName.substring(taskName.indexOf("/by ") + 4), INPUT_DATE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new NephilimInputException("event " + taskName, "Could not understand the date "
                    + e.getParsedString() + ". Please ensure date is in YYYY-MM-DD TTTT format, with time in 24 Hour Time.");
        }
    }
    */


    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by: " + byDate.format(OUTPUT_DATE_FORMAT) + ")";
    }

    @Override
    public String encode() {
        return " deadline " + super.encode() + " /by " + byDate.format(INPUT_DATE_FORMAT);
    }
}
