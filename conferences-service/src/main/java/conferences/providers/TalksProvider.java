package conferences.providers;

import conferences.api.dto.TalkRequest;
import conferences.domain.Talk;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

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
}
