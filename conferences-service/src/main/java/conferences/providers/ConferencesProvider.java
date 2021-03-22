package conferences.providers;


import conferences.api.dto.ConferenceRequest;
import conferences.api.dto.ConferenceResponse;
import conferences.domain.Conference;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.function.BiFunction;
import java.util.function.Function;

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

    public Function<Conference, ConferenceResponse> toResponse() {
        return c -> new ConferenceResponse().id(c.getId())
                .name(c.getName())
                .topic(c.getTopic())
                .dateStart(c.getDateStart().toString())
                .dateEnd(c.getDateEnd().toString())
                .participants(c.getParticipants());
    }

}
