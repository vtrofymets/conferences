package org.vt.conferences.api.test.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;
import org.vt.conferences.api.test.service.ExpConferenceService;

@Configuration
public class ExpConferenceRestController {

    @Bean
    public RouterFunction<ServerResponse> as(ExpConferenceService expConferenceService) {
        return RouterFunctions.route()
                .GET("/default", expConferenceService)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> zxc(ExpConferenceService expConferenceService) {
        return RouterFunctions.route(sr -> "/test".equals(sr.uri()
                .getPath()), r -> ServerResponse.ok()
                .body("TEST"));
    }
}

