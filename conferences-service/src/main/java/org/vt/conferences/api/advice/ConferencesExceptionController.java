package org.vt.conferences.api.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.vt.conferences.exceptions.ConferenceException;
import org.vt.conferences.exceptions.TalkException;

@ControllerAdvice
@Slf4j
public class ConferencesExceptionController {

    @ExceptionHandler({ConferenceException.class, TalkException.class})
    public ResponseEntity<String> handleConference(ConferenceException ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    }

    //    @ExceptionHandler(TalkException.class)
    //    public ResponseEntity<String> handleConference(TalkException ex) {
    //        log.error(ex.getMessage());
    //        return new ResponseEntity<>(ex.getMessage(), ex.getStatus());
    //    }
}
