package org.vt.conferences.mappers;


import conferences.api.dto.ConferenceRequest;
import conferences.api.dto.ConferenceResponse;
import org.springframework.stereotype.Component;
import org.vt.conferences.domain.Conference;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ConferencesMapper {

    public Conference map(Integer integer, ConferenceRequest conference) {
        return Conference.builder()
                .id(integer)
                .name(conference.getName())
                .topic(conference.getTopic())
                .dateStart(LocalDate.parse(conference.getDateStart()))
                .dateEnd(LocalDate.parse(conference.getDateEnd()))
                .participants(conference.getParticipants())
                .build();
    }

    public List<ConferenceResponse> mapToList(List<Conference> conferences) {
        return conferences.stream()
                .map(toResponse())
                .collect(Collectors.toList());
    }

    private Function<Conference, ConferenceResponse> toResponse() {
        return c -> new ConferenceResponse().id(c.getId())
                .name(c.getName())
                .topic(c.getTopic())
                .dateStart(c.getDateStart()
                        .toString())
                .dateEnd(c.getDateEnd()
                        .toString())
                .participants(c.getParticipants());
    }

}
