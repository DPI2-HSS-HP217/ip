import java.util.StringTokenizer;

public class Deadline extends Task {
    private String bydate;

    //to clean up exception error handling by merging into a more concise form where possible
    public Deadline(String taskName) throws NephilimInputException {
        super(taskName); //initializing with wrong name first to get past
        //"super must be first line in constructor" requirement.
        if (!taskName.contains("/by ")) {
            throw new NephilimInputException("deadline " + taskName, "Deadline field (denoted after \"/by\") is " +
                                             "either empty or missing the \"/by\" flag");
        } else if (taskName.indexOf("/by") == 1) {
            throw new NephilimInputException("deadline" + taskName, "Deadline task name cannot be empty.");
        }
        super.setTaskName(taskName.substring(0, taskName.indexOf("/by ")));
        this.bydate = taskName.substring(taskName.indexOf("/by ") + 4);
    }


    @Override
    public String toString() {
        return "[D]" + super.toString() + "(by: " + bydate + ")";
    }
}
