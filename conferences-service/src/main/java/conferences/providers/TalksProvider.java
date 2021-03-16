package conferences.providers;

import conferences.api.dto.TalkDto;
import conferences.domain.Talk;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class TalksProvider implements BiFunction<Integer, TalkDto, Talk> {

    @Override
    public Talk apply(Integer conferenceId, TalkDto talk) {
        return Talk.builder()
                .conferenceId(conferenceId)
                .title(talk.getTitle())
                .description(talk.getDescription())
                .speaker(talk.getSpeaker())
                .type(talk.getTalkType().name())
                .build();
    }
}
