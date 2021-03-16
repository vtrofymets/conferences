package conferences.domain;

import conferences.api.dto.ConferenceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String topic;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private int participants;

    public static ConferenceDto to(Conference entity) {
        return new ConferenceDto().id(entity.getId())
                .name(entity.getName())
                .topic(entity.getTopic())
                .dateStart(entity.getDateStart().toString())
                .dateEnd(entity.getDateEnd().toString())
                .participants(entity.getParticipants());
    }
}
