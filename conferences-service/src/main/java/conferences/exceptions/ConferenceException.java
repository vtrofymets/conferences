package conferences.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ConferenceException extends RuntimeException {

    private final HttpStatus status;

    public ConferenceException(String message, HttpStatus status) {
        super(message, null, false, false);
        this.status = status;
    }
}
