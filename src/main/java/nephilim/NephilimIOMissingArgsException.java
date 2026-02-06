package nephilim;

/**
 * Represents an exception related to missing arguments in chatbot input.
 */
class NephilimIOMissingArgsException extends NephilimException {
    private String message;
    private String input;

    public NephilimIOMissingArgsException(String input, String message) {
        super(message);
        this.input = input;
    }

    @Override
    public String toString() {
        return "The following input could not be understood: " + input
                + '\n' + "Additional details: " + getMessage();
    }
}
