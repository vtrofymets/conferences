package conferences.service;

import conferences.api.dto.ConferenceDto;

import java.util.List;

public interface ConferencesService {

    int addNewConference(ConferenceDto conference);

    List<ConferenceDto> receiveAllConferences();

    void updateConference(Integer conferenceId, ConferenceDto conference);
}
