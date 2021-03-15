package conferences.domain;

import conferences.api.dto.Talk;
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
@Table(name = "talk")
public class TalkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer conferenceId;
    private String talkName;
    private String talkDescription;
    private String speaker;
    private String talkType;

    public static Talk to(TalkEntity entity){
        Talk talk = new Talk();
        talk.setTalkName(entity.getTalkName());
        talk.setTalkDescription(entity.getTalkDescription());
        talk.setSpeaker(entity.getSpeaker());
        talk.setTalkType(Talk.TalkTypeEnum.fromValue(entity.getTalkType()));
        return talk;
    }

}
