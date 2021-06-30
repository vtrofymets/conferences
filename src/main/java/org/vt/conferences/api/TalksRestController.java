package org.vt.conferences.api;


import conferences.api.TalksApi;
import conferences.api.dto.TalkRequest;
import conferences.api.dto.TalkResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.vt.conferences.service.TalksService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TalksRestController implements TalksApi {

    private final TalksService talksService;

    @Override
    public ResponseEntity<Void> addTalk(@Min(1) Long conferenceId, @Valid TalkRequest body) {
        talksService.addTalkToConference(conferenceId, body);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<TalkResponse>> retrieveTalksByConferenceId(@Min(1) Long conferenceId) {
        log.info("Get Talks by conferenceId[{}]", conferenceId);
        return new ResponseEntity<>(talksService.receiveConferenceTalks(conferenceId), HttpStatus.OK);
    }
}
