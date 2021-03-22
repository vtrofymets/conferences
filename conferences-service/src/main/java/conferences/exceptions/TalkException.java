package conferences.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TalkException extends ConferenceException {

    public TalkException(String message, HttpStatus status) {
        super(message, status);
    }
}
