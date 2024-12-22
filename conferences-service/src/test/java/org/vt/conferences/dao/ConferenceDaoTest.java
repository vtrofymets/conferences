package org.vt.conferences.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.vt.conferences.domain.Conference;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Vlad Trofymets
 */
@SpringBootTest
@ActiveProfiles("test")
public class ConferenceDaoTest {

    public static final String TOPIC = "TOPIC";
    public static final String NAME = "NAME";
    public static final String NAME_2 = "NAME2";
    public static final String NAME_3 = "NAME3";

    @Autowired
    private ConferenceDao conferenceDao;

    @BeforeEach
    void beforeEach() {
        conferenceDao.saveAll(conferences());
    }

    @AfterEach
    void afterEach() {
        conferenceDao.deleteAll();
    }

    @Test
    void savedConferenceTest() {
        var conferences = conferenceDao.findAll();

        Assertions.assertThat(conferences)
                .isNotNull()
                .isNotEmpty()
                .hasSize(3)
                .extracting(Conference::getId)
                .isNotNull()
                .isNotEmpty()
                .hasSize(3)
                .doesNotContainNull();
    }

    @Test
    void findByNameTest() {
        var optional = conferenceDao.findByName(NAME);

        var actual = optional.orElseThrow();

        Assertions.assertThat(actual.getName())
                .isEqualTo(NAME);
    }

    @Test
    void checkOnExistPeriodWhereReturnOneTest() {
        int actual = conferenceDao.checkOnExistPeriod(NAME, LocalDate.now(), LocalDate.now());

        Assertions.assertThat(actual)
                .isEqualTo(1);
    }

    @Test
    void findAllActiveTest() {
        var actual = conferenceDao.findAllActive();

        Assertions.assertThat(actual)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .extracting(Conference::getId)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .doesNotContainNull();
    }

    private List<Conference> conferences() {
        return List.of(Conference.builder()
                .topic(TOPIC)
                .name(NAME)
                .participants(111)
                .dateStart(LocalDate.now()
                        .plusDays(31))
                .dateEnd(LocalDate.now()
                        .plusDays(38))
                .build(), Conference.builder()
                .topic(TOPIC)
                .name(NAME_2)
                .participants(111)
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now())
                .build(), Conference.builder()
                .topic(TOPIC)
                .name(NAME_3)
                .participants(111)
                .dateStart(LocalDate.now()
                        .minusYears(1))
                .dateEnd(LocalDate.now()
                        .minusYears(1))
                .build());
    }
}
