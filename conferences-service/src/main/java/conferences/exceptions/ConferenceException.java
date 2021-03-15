package conferences.exceptions;

public class ConferenceException extends RuntimeException {
    public ConferenceException(String message) {
        super(message, null, false, false);
    }
}
