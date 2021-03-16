package conferences.api;


import conferences.api.dto.TalkDto;
import conferences.service.TalksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TalksRestController implements TalksApi {

    private final TalksService talksService;

    @Override
    public ResponseEntity<Void> addTalk(@Min(1) Integer conferenceId, @Valid TalkDto body) {
        talksService.addNewTalkToConference(conferenceId, body);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<TalkDto>> retrieveTalksByConferenceId(@Min(1) Integer conferenceId) {
        return new ResponseEntity<>(talksService.receiveAllTalksByConferenceId(conferenceId), HttpStatus.OK);
    }
}
