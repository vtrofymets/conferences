package conferences.api;


import conferences.api.TalksApi;
import conferences.api.dto.Talk;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
public class TalksRestController implements TalksApi {

    @Override
    public ResponseEntity<Void> addTalk(@Min(1) Integer conferenceId, @Valid Talk body) {
        return null;
    }

    @Override
    public ResponseEntity<List<Talk>> retrieveTalksByConferenceId(@Min(1) Integer conferenceId) {
        return null;
    }
}
