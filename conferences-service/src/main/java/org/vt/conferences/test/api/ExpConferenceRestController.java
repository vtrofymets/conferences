package org.vt.conferences.test.api;

import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;
import org.vt.conferences.test.annotation.ConferencesRestController;
import org.vt.conferences.test.annotation.RestControllers;
import org.vt.conferences.test.service.ExpConferenceService;

@RestControllers
public class ExpConferenceRestController {

    @ConferencesRestController
    public RouterFunction<ServerResponse> as(ExpConferenceService expConferenceService) {
        return RouterFunctions.route()
                .GET("/default", expConferenceService)
                .build();
    }

    @ConferencesRestController
    public RouterFunction<ServerResponse> zxc(ExpConferenceService expConferenceService) {
        return RouterFunctions.route(sr -> "/test".equals(sr.uri()
                .getPath()), r -> ServerResponse.ok()
                .body("JEST"));
    }
}

