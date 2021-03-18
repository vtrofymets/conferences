package conferences.providers;


import conferences.api.dto.ConferenceRequest;
import conferences.domain.Conference;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.function.BiFunction;

@Component
public class ConferencesProvider implements BiFunction<Integer, ConferenceRequest, Conference> {

    @Override
    public Conference apply(Integer integer, ConferenceRequest conference) {
        return Conference.builder()
                .id(integer)
                .name(conference.getName())
                .topic(conference.getTopic())
                .dateStart(LocalDate.parse(conference.getDateStart()))
                .dateEnd(LocalDate.parse(conference.getDateEnd()))
                .participants(conference.getParticipants())
                .build();
    }
}
