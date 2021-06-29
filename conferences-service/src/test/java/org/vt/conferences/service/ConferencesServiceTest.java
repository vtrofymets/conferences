package org.vt.conferences.service;

import conferences.api.dto.ConferenceRequest;
import conferences.api.dto.ConferenceResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.vt.conferences.ConferencesServiceApplication;
import org.vt.conferences.dao.ConferenceDao;
import org.vt.conferences.domain.Conference;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Vlad Trofymets
 */
@SpringBootTest(classes = ConferencesServiceApplication.class)
@ActiveProfiles("test")
public class ConferencesServiceTest {

    public static final String NAME = "NAME";
    public static final String TOPIC = "TOPIC";
    public static final int PARTICIPANTS = 111;
    public static final LocalDate DATE_START = LocalDate.now()
            .plusDays(1);
    public static final LocalDate DATE_END = LocalDate.now()
            .plusDays(6);

    @Autowired
    private ConferencesService conferencesService;

    @MockBean
    private ConferenceDao conferenceDao;

    @Test
    void addConferenceTest() {
        var request = new ConferenceRequest().topic(TOPIC)
                .name(NAME)
                .dateStart(DATE_START
                        .toString())
                .dateEnd(DATE_END
                        .toString())
                .participants(PARTICIPANTS);

        var conference = Conference.builder()
                .id(1)
                .build();
        Mockito.when(conferenceDao.save(Mockito.any()))
                .thenReturn(conference);

        int actual = conferencesService.addConference(request);

        Assertions.assertThat(actual)
                .isNotNull()
                .isEqualTo(conference.getId());
    }

    @Test
    void receiveConferencesTest() {
        var conference = Conference.builder()
                .id(1)
                .participants(PARTICIPANTS)
                .dateStart(DATE_START)
                .dateEnd(DATE_END)
                .name(NAME)
                .topic(TOPIC)
                .build();
        Mockito.when(conferenceDao.findAll())
                .thenReturn(List.of(conference));

        var actual = conferencesService.receiveConferences(Boolean.TRUE);

        var expected = new ConferenceResponse().id(1)
                .name(NAME)
                .topic(TOPIC)
                .participants(PARTICIPANTS)
                .dateStart(DATE_START
                        .toString())
                .dateEnd(DATE_END.toString());

        Assertions.assertThat(actual).isEqualTo(List.of(expected));
    }
}
