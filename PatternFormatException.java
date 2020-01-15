public class PatternFormatException extends Exception {
    public PatternFormatException() {
        super("Invalid pattern format");
    }

    public PatternFormatException(String message) {
        super(message);
    }
}
