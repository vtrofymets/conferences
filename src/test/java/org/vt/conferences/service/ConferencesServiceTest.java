package org.vt.conferences.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.vt.conferences.dao.ConferenceDao;
import org.vt.conferences.domain.Conference;
import org.vt.conferences.service.validations.ValidationsService;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Vlad Trofymets
 */
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ConferencesServiceTest {

    public static final String NAME = "NAME";
    public static final String TOPIC = "TOPIC";
    public static final int PARTICIPANTS = 111;
    public static final LocalDate DATE_START = LocalDate.now()
            .plusDays(1);
    public static final LocalDate DATE_END = LocalDate.now()
            .plusDays(6);

    @InjectMocks
    private ConferencesServiceImpl conferencesService;
    @Mock
    private ValidationsService validationsService;
    @Mock
    private ConferenceDao conferenceDao;

    @Test
    void addConferenceTest() {
        var expected = Conference.builder()
                .id(1L)
                .build();
        validationsService.validation(Mockito.any(), Mockito.anyList());
        Mockito.when(conferenceDao.save(Mockito.any()))
                .thenReturn(expected);

        var actual = conferencesService.saveConference(expected);

        Assertions.assertThat(actual.getId())
                .isNotNull()
                .isEqualTo(expected.getId());
    }

    @Test
    void receiveConferencesTest() {
        var expcted = Conference.builder()
                .id(1L)
                .participants(PARTICIPANTS)
                .dateStart(DATE_START)
                .dateEnd(DATE_END)
                .name(NAME)
                .topic(TOPIC)
                .build();
        Mockito.when(conferenceDao.findAll())
                .thenReturn(List.of(expcted));

        var actual = conferencesService.receiveConferences(Boolean.TRUE);

        Assertions.assertThat(actual)
                .isEqualTo(List.of(expcted));
    }
}
