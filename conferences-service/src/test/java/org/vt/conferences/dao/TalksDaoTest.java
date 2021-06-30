package org.vt.conferences.dao;

import conferences.api.dto.TalkType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.vt.conferences.ConferencesServiceApplication;
import org.vt.conferences.domain.Conference;
import org.vt.conferences.domain.Talk;

import java.time.LocalDate;

/**
 * @author Vlad Trofymets
 */
@DataJpaTest
@ContextConfiguration(classes = ConferencesServiceApplication.class)
public class TalksDaoTest {

    public static final String TITLE = "TITLE";
    public static final String SPEAKER = "SPEAKER";
    public static final String DESCRIPTION = "DESCRIPTION";

    @Autowired
    private TalksDao talksDao;
    @Autowired
    private ConferenceDao conferenceDao;

    @BeforeEach
    void beforeEach() {
        var conference = conferenceDao.save(Conference.builder()
                .participants(111)
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now())
                .name("NAME")
                .topic("TOPIC")
                .build());

        talksDao.save(talk(conference.getId()));
    }

    @Test
    void savedTalk() {
        var all = talksDao.findAll();

        Assertions.assertThat(all)
                .isNotEmpty()
                .isNotNull()
                .extracting(Talk::getId)
                .doesNotContainNull();
    }

    @Test
    void findByConferenceIdTest() {
        var actual = talksDao.findAll();

        Assertions.assertThat(actual)
                .doesNotContainNull()
                .hasSize(1)
                .extracting(Talk::getConferenceId)
                .doesNotContainNull();
    }

    @Test
    void existsByConferenceIdAndTitleTest() {
        var actual = talksDao.existsByConferenceIdAndTitle(getId(), TITLE);

        Assertions.assertThat(actual)
                .isTrue();
    }

    @Test
    void findByConferenceIdAndSpeakerTest() {
        var actual = talksDao.findByConferenceIdAndSpeaker(getId(), SPEAKER);

        Assertions.assertThat(actual)
                .doesNotContainNull()
                .hasSize(1)
                .extracting(Talk::getSpeaker)
                .containsAnyOf(SPEAKER);
    }

    private Long getId() {
        return conferenceDao.findAll()
                .stream()
                .findFirst()
                .map(Conference::getId)
                .orElseThrow();
    }

    private Talk talk(Long confId) {
        return Talk.builder()
                .conferenceId(confId)
                .title(TITLE)
                .speaker(SPEAKER)
                .description(DESCRIPTION)
                .type(TalkType.MASTER_CLASS)
                .build();
    }
}
