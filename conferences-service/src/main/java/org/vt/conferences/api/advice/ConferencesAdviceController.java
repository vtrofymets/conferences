package org.vt.conferences.api.advice;

import conferences.api.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.vt.conferences.exceptions.ConferenceException;
import org.vt.conferences.exceptions.TalkException;

@RestControllerAdvice
@Slf4j
public class ConferencesAdviceController {

    @ExceptionHandler({ConferenceException.class, TalkException.class})
    public ResponseEntity<ErrorResponse> handleConferenceException(ConferenceException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(ex.getCode())
                .body(new ErrorResponse().errorMessage(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        return new ErrorResponse().errorMessage(ex.getMessage());
    }

}
