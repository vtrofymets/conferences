package org.vt.conferences.service.validations;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vt.conferences.dao.ConferenceDao;
import org.vt.conferences.domain.Conference;
import org.vt.conferences.exceptions.ConferenceException;
import org.vt.conferences.service.validations.conference.ExistConferenceValidation;
import org.vt.conferences.service.validations.conference.PeriodConferenceValidation;
import org.vt.conferences.service.validations.conference.UniqueNameConferenceValidation;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author Vlad Trofymets
 */
@ExtendWith(MockitoExtension.class)
class ConferencesValidationTest {

    @Mock
    private ConferenceDao conferenceDao;
    @InjectMocks
    private ExistConferenceValidation existConferenceValidation;
    @InjectMocks
    private PeriodConferenceValidation periodConferenceValidation;
    @InjectMocks
    private UniqueNameConferenceValidation uniqueNameConferenceValidation;

    @Test
    void conferenceExistValidationTest() {
        var conference = conference(3453535L);
        Mockito.when(conferenceDao.existsById(conference.getId()))
                .thenReturn(Boolean.FALSE);

        checkThrowBy(() -> existConferenceValidation.validate(conference));
    }

    @Test
    void periodValidationTest() {
        var conference = conference(1L);
        Mockito.when(conferenceDao.checkOnExistPeriod(conference.getName(), conference.getDateStart(),
                        conference.getDateEnd()))
                .thenReturn(1);

        checkThrowBy(() -> periodConferenceValidation.validate(conference));
    }

    @Test
    void uniqueNameValidationTest() {
        var conference = conference(null);
        Mockito.when(conferenceDao.findByName(conference.getName()))
                .thenReturn(Optional.of(conference));

        checkThrowBy(() -> uniqueNameConferenceValidation.validate(conference));
    }

    private void checkThrowBy(ThrowableAssert.ThrowingCallable call) {
        Assertions.assertThatThrownBy(call)
                .isInstanceOf(ConferenceException.class)
                .hasFieldOrProperty("code")
                .hasFieldOrProperty("message");
    }


    private Conference conference(Long id) {
        return Conference.builder()
                .id(id)
                .name("NAME")
                .topic("TOPIC")
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now()
                        .plusDays(1))
                .participants(111)
                .build();
    }

}