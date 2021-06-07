package org.vt.conferences.service;

import conferences.api.dto.TalkRequest;
import conferences.api.dto.TalkResponse;

import java.util.List;

public interface TalksService {

    void addTalkToConference(Integer conferenceId, TalkRequest talk);

    List<TalkResponse> receiveConferenceTalks(Integer id);
}
