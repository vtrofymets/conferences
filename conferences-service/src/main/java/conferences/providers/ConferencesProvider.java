package conferences.providers;

import conferences.api.dto.Conference;
import conferences.domain.ConferenceEntity;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class ConferencesProvider implements BiFunction<Integer, Conference, ConferenceEntity> {

    @Override
    public ConferenceEntity apply(Integer integer, Conference conference) {
        return ConferenceEntity.builder()
                .id(integer)
                .conferenceName(conference.getConfName())
                .conferenceTopic(conference.getConfTopic())
                .conferenceDate(conference.getConfDate())
                .participants(conference.getParticipants())
                .build();
    }
}
