package org.vt.conferences.api;


import conferences.api.TalksApi;
import conferences.api.dto.TalkCreatedResponse;
import conferences.api.dto.TalkRequest;
import conferences.api.dto.TalkResponse;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.vt.conferences.mappers.TalkMapper;
import org.vt.conferences.service.TalksService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TalksRestController implements TalksApi {

    private final TalksService talksService;
    private final TalkMapper talkMapper;

    @Override
    @ResponseBody
    @Timed(value = "add_new_talk_time")
    @Counted(value = "add_new_talk_time_count")
    public TalkCreatedResponse addNewTalk(@Min(1) Long conferenceId, @Valid TalkRequest request) {
        var talk = talkMapper.map(conferenceId, request);
        return new TalkCreatedResponse().id(talksService.addTalkToConference(talk)
                .getId());
    }

    @Override
    @ResponseBody
    @Timed(value = "receive_talk_by_id_time")
    @Counted(value = "receive_talk_by_id_count")
    public TalkResponse receiveTalkById(@Min(1L) Long talkId) {
        var talk = talksService.findTalk(talkId);
        return talkMapper.map(talk);
    }

    @Override
    @ResponseBody
    @Timed(value = "receive_talks_by_conference_id_time")
    @Counted(value = "receive_talks_by_conference_id_count")
    public List<TalkResponse> receiveTalksByConferenceId(@Min(1) Long conferenceId) {
        log.info("Get Talks by conferenceId[{}]", conferenceId);
        return talksService.receiveConferenceTalks(conferenceId)
                .stream()
                .map(talkMapper::map)
                .toList();
    }

}
