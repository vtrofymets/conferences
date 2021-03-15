package conferences.api;


import conferences.api.dto.Conference;
import conferences.service.ConferencesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConferencesRestController implements ConferencesApi {

    private final ConferencesService conferencesService;

    @Override
    public ResponseEntity<Integer> addConference(@Valid Conference body) {
        return new ResponseEntity<>(conferencesService.addNewConference(body), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Conference>> allConferences() {
        return new ResponseEntity<>(conferencesService.receiveAllConferences(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateConference(@Min(1) Integer conferenceId, @Valid Conference body) {
        conferencesService.updateConference(conferenceId, body);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
