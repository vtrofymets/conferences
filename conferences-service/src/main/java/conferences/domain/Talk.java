package conferences.domain;

import conferences.api.dto.TalkResponse;
import conferences.api.dto.TalkType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Talk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer conferenceId;
    private String title;
    private String description;
    private String speaker;
    private String type;

    public static TalkResponse to(Integer conferenceId, Talk entity) {
        return new TalkResponse().conferenceId(conferenceId)
                .title(entity.getTitle())
                .description(entity.getDescription())
                .speaker(entity.getSpeaker())
                .talkType(TalkType.fromValue(entity.getType()));
    }

}
