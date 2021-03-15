package conferences.service;

import conferences.api.dto.Conference;

import java.util.List;

public interface ConferencesService {

    int addNewConference(Conference conference);

    List<Conference> receiveAllConferences();

    void updateConference(Integer conferenceId, Conference conference);
}
