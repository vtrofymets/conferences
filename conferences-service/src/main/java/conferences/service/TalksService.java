package conferences.service;

import conferences.api.dto.Talk;

import java.util.List;

public interface TalksService {

    void addNewTalkToConference(Integer conferenceId, Talk talk);

    List<Talk> receiveAllTalksByConferenceId(Integer id);
}
