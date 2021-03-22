package conferences.providers;

import conferences.api.dto.TalkRequest;
import conferences.api.dto.TalkResponse;
import conferences.api.dto.TalkType;
import conferences.domain.Talk;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;
import java.util.function.Function;

@Component
public class TalksProvider implements BiFunction<Integer, TalkRequest, Talk> {

    @Override
    public Talk apply(Integer conferenceId, TalkRequest talk) {
        return Talk.builder()
                .conferenceId(conferenceId)
                .title(talk.getTitle())
                .description(talk.getDescription())
                .speaker(talk.getSpeaker())
                .type(talk.getTalkType().name())
                .build();
    }

    public Function<Talk, TalkResponse> toResponse() {
        return t -> new TalkResponse().conferenceId(t.getConferenceId())
                .title(t.getTitle())
                .description(t.getDescription())
                .speaker(t.getSpeaker())
                .talkType(TalkType.fromValue(t.getType()));
    }
}
