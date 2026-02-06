package nephilim;

public class NephilimException extends Exception {
    private String message;

    public NephilimException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Generic error: " + getMessage();
    }
}
