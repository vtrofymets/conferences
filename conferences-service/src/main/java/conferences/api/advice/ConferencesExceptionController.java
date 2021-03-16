package conferences.api.advice;

import conferences.exceptions.ConferenceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ConferencesExceptionController {

    @ExceptionHandler(ConferenceException.class)
    public ResponseEntity<String> handleConference(ConferenceException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }
}
