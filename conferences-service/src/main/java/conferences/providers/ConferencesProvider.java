package conferences.providers;

import conferences.api.dto.ConferenceDto;
import conferences.domain.Conference;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.function.BiFunction;

@Component
public class ConferencesProvider implements BiFunction<Integer, ConferenceDto, Conference> {

    @Override
    public Conference apply(Integer integer, ConferenceDto conference) {
        return Conference.builder()
                .id(integer)
                .name(conference.getName())
                .topic(conference.getTopic())
                .dateStart(LocalDate.parse(conference.getDateStart()))
                .dateStart(LocalDate.parse(conference.getDateEnd()))
                .participants(conference.getParticipants())
                .build();
    }
}
