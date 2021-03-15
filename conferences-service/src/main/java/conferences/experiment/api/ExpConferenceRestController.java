package conferences.experiment.api;

import conferences.experiment.annotation.RestControllers;
import conferences.experiment.annotation.ConferencesRestController;
import conferences.experiment.service.ExpConferenceService;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

@RestControllers
public class ExpConferenceRestController {

    @ConferencesRestController
    public RouterFunction<ServerResponse> as(ExpConferenceService expConferenceService) {
        return RouterFunctions.route().GET("/default", expConferenceService).build();
    }

    @ConferencesRestController
    public RouterFunction<ServerResponse> zxc(ExpConferenceService expConferenceService) {
        return RouterFunctions.route(sr -> "/test".equals(sr.uri().getPath()), r -> ServerResponse.ok().body("JEST"));
    }
}

