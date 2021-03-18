package conferences.service;


import conferences.api.dto.ConferenceRequest;
import conferences.api.dto.ConferenceResponse;

import java.util.List;

public interface ConferencesService {

    int addNewConference(ConferenceRequest conference);

    List<ConferenceResponse> receiveAllConferences();

    void updateConference(Integer conferenceId, ConferenceRequest conference);
}
