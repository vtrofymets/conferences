package conferences.providers;

import conferences.api.dto.Talk;
import conferences.domain.TalkEntity;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class TalksProvider implements BiFunction<Integer, Talk, TalkEntity> {

    @Override
    public TalkEntity apply(Integer conferenceId, Talk talk) {
        return TalkEntity.builder()
                .conferenceId(conferenceId)
                .talkName(talk.getTalkName())
                .talkDescription(talk.getTalkDescription())
                .speaker(talk.getSpeaker())
                .talkType(talk.getTalkType().name())
                .build();
    }
}
