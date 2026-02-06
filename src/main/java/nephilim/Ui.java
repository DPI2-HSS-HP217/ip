package nephilim;
/**
 * Handles all interactions with user: formats responses, etc.
 * Also stores all responses to user.
 */

class Ui {

    private static final String LINE_BREAK = "____________________________________________________________";

    /**
     * Formats a given String by placing it in between two horizontal lines.
     * @param out String to be printed to user.
     */
    public void print(String out) {
        System.out.println(LINE_BREAK);
        System.out.println(out);
        System.out.println(LINE_BREAK);
    }

    /**
     *  Formats a given string to be printed as an exception rather than the usual
     *  status message.
     * @param msg Exception message to be printed.
     * @param details Further details about exception
     */
    public void printException(String msg, String details) {
        print("Exception: " + msg + "\nExtra details: " + details);
    }

    /**
     *  Formats a given string to be printed as an exception rather than the usual
     *  status message. Overloaded version of above, if details do not exist.
     * @param msg Exception message to be printed.
     */
    public void printException(String msg) {
        print("Exception: " + msg);
    }

}
