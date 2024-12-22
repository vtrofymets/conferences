package org.vt.conferences.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ConferenceException extends RuntimeException {
    private final HttpStatus code;

    public ConferenceException(String message, HttpStatus code) {
        super(message, null, false, false);
        this.code = code;
    }
}
