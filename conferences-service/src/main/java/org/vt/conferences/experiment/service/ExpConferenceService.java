package org.vt.conferences.experiment.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@Service
public class ExpConferenceService implements HandlerFunction<ServerResponse> {
    @Override
    public ServerResponse handle(ServerRequest serverRequest) {
        return ServerResponse.status(HttpStatus.OK)
                .body("HAPPY");
    }
}
