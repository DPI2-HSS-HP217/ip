package nephilim;

public class NephilimIOBadArgsException extends NephilimException {
    private String message;
    private String input;

    public NephilimIOBadArgsException(String input, String message) {
        super(message);
        this.input = input;
    }

    @Override
    public String toString() {
        return "The following input could not be understood: " + input
                + '\n' + "Additional details: " + getMessage();
    }
}
