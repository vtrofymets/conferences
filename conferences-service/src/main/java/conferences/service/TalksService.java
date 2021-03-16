package conferences.service;

import conferences.api.dto.TalkDto;

import java.util.List;

public interface TalksService {

    void addNewTalkToConference(Integer conferenceId, TalkDto talk);

    List<TalkDto> receiveAllTalksByConferenceId(Integer id);
}
