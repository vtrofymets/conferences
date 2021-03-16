package conferences.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TalkException extends RuntimeException {

    private final HttpStatus status;

    public TalkException(String message, HttpStatus status) {
        super(message, null, false, false);
        this.status = status;
    }
}
