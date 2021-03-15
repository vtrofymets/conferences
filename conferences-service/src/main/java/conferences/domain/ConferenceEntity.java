package conferences.domain;

import conferences.api.dto.Conference;
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
@Table(name = "conference")
public class ConferenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String conferenceName;
    private String conferenceTopic;
    private LocalDate conferenceDate;
    private int participants;

    public static Conference to(ConferenceEntity entity) {
        Conference conference = new Conference();
        conference.confId(entity.getId());
        conference.confName(entity.getConferenceName());
        conference.setConfTopic(entity.getConferenceTopic());
        conference.setConfDate(entity.getConferenceDate().toString());
        conference.setParticipants(entity.getParticipants());
        return conference;
    }
}
