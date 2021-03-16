package conferences.domain;

import conferences.api.dto.TalkDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public static TalkDto to(Talk entity) {
        return new TalkDto().title(entity.getTitle())
                .description(entity.getDescription())
                .speaker(entity.getSpeaker())
                .talkType(TalkDto.TalkTypeEnum.fromValue(entity.getType()));
    }

}
