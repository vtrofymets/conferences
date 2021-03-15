package conferences.domain;

import conferences.api.dto.Conference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.threeten.bp.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ConferenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String conferenceName;
    private String conferenceTopic;
    private LocalDate conferenceDate;
    private int participants;

    public static Conference to(ConferenceEntity entity) {
        Conference conference = new Conference();
        conference.confName(entity.getConferenceName());
        conference.setConfTopic(entity.getConferenceTopic());
        conference.setConfDate(entity.getConferenceDate());
        conference.setParticipants(entity.getParticipants());
        return conference;
    }
}
