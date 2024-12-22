package org.vt.conferences.service;

import lombok.NonNull;
import org.vt.conferences.domain.Talk;

import java.util.List;

public interface TalksService {

    Talk addTalkToConference(@NonNull Talk talk);

    List<Talk> receiveConferenceTalks(@NonNull Long conferenceId);

    Talk findTalk(@NonNull Long talkId);
}
