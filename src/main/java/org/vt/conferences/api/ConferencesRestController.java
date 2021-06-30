package org.vt.conferences.api;


import conferences.api.ConferencesApi;
import conferences.api.dto.ConferenceRequest;
import conferences.api.dto.ConferenceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.vt.conferences.service.ConferencesService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ConferencesRestController implements ConferencesApi {

    private final ConferencesService conferencesService;

    @Override
    public ResponseEntity<Long> addConference(@Valid ConferenceRequest body) {
        log.info("Add Conference Body Request{}", body);
        return new ResponseEntity<>(conferencesService.addConference(body), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<ConferenceResponse>> allConferences(Boolean entirePeriod) {
        log.info("Receive conferences for entire period={}", entirePeriod);
        var conferences = conferencesService.receiveConferences(entirePeriod);
        log.info("Conferences[{}]", conferences.size());
        return new ResponseEntity<>(conferences, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateConference(@Min(1) Long conferenceId, @Valid ConferenceRequest body) {
        log.info("Update Conference By conferenceId{}, body{}", conferenceId, body);
        conferencesService.updateConference(conferenceId, body);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
