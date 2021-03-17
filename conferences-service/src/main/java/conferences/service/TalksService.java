package conferences.service;

import conferences.api.dto.TalkRequest;
import conferences.api.dto.TalkResponse;

import java.util.List;

public interface TalksService {

    void addNewTalkToConference(Integer conferenceId, TalkRequest talk);

    List<TalkResponse> receiveAllTalksByConferenceId(Integer id);
}
