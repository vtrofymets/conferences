package org.vt.conferences.mappers;

import conferences.api.dto.TalkRequest;
import conferences.api.dto.TalkResponse;
import org.springframework.stereotype.Component;
import org.vt.conferences.domain.Talk;

import java.util.function.Function;

@Component
public class TalksProvider {

    public Talk map(Long conferenceId, TalkRequest talk) {
        return Talk.builder()
                .conferenceId(conferenceId)
                .title(talk.getTitle())
                .description(talk.getDescription())
                .speaker(talk.getSpeaker())
                .type(talk.getTalkType())
                .build();
    }

    public Function<Talk, TalkResponse> toResponse() {
        return t -> new TalkResponse().conferenceId(t.getConferenceId())
                .title(t.getTitle())
                .description(t.getDescription())
                .speaker(t.getSpeaker())
                .talkType(t.getType());
    }
}
