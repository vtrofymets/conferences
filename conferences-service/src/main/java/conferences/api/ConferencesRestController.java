package conferences.api;


import conferences.api.dto.ConferenceRequest;
import conferences.api.dto.ConferenceResponse;
import conferences.service.ConferencesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ConferencesRestController implements ConferencesApi {

    private final ConferencesService conferencesService;

    @Override
    public ResponseEntity<Integer> addConference(@Valid ConferenceRequest body) {
        log.info("Add Conference Body Request{}", body);
        return new ResponseEntity<>(conferencesService.addNewConference(body), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<ConferenceResponse>> allConferences() {
        return new ResponseEntity<>(conferencesService.receiveAllConferences(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateConference(@Min(1) Integer conferenceId, @Valid ConferenceRequest body) {
        log.info("Update Conference By conferenceId{}, body{}", conferenceId, body);
        conferencesService.updateConference(conferenceId, body);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
