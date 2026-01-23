public class NephilimException extends Exception {
    private String message;
    //generic wrapper exception for now?
    public NephilimException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Generic error: " + getMessage();
    }
}
