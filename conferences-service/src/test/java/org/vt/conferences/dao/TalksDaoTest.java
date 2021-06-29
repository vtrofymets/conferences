package org.vt.conferences.dao;

import conferences.api.dto.TalkType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.vt.conferences.ConferencesServiceApplication;
import org.vt.conferences.domain.Conference;
import org.vt.conferences.domain.Talk;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Vlad Trofymets
 */
@DataJpaTest
@ContextConfiguration(classes = ConferencesServiceApplication.class)
public class TalksDaoTest {

    public static final int CONFERENCE_ID = 1;
    public static final String TITLE = "TITLE";
    public static final String SPEAKER = "SPEAKER";
    public static final String DESCRIPTION = "DESCRIPTION";

    @Autowired
    private TalksDao talksDao;
    @Autowired
    private ConferenceDao conferenceDao;

    @BeforeEach
    void beforeEach() {
        conferenceDao.save(Conference.builder()
                .id(1)
                .participants(111)
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now())
                .name("NAME")
                .topic("TOPIC")
                .build());

        talksDao.save(talk());
    }

    @AfterEach
    void afterEach() {

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
        var actual = talksDao.findByConferenceId(CONFERENCE_ID);

        Assertions.assertThat(actual)
                .doesNotContainNull()
                .hasSize(1)
                .extracting(Talk::getConferenceId)
                .containsAnyOf(CONFERENCE_ID);
    }

    @Test
    void existsByConferenceIdAndTitleTest() {
        var actual = talksDao.existsByConferenceIdAndTitle(CONFERENCE_ID, TITLE);

        Assertions.assertThat(actual)
                .isTrue();
    }

    @Test
    void findByConferenceIdAndSpeakerTest() {
        var actual = talksDao.findByConferenceIdAndSpeaker(CONFERENCE_ID, SPEAKER);

        Assertions.assertThat(actual)
                .doesNotContainNull()
                .hasSize(1)
                .extracting(Talk::getSpeaker)
                .containsAnyOf(SPEAKER);
    }

    private Talk talk() {
        return Talk.builder()
                .conferenceId(CONFERENCE_ID)
                .title(TITLE)
                .speaker(SPEAKER)
                .description(DESCRIPTION)
                .type(TalkType.MASTER_CLASS)
                .build();
    }
}
