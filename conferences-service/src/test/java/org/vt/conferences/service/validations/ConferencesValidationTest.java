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
    private ConferenceExistValidation conferenceExistValidation;
    @InjectMocks
    private PeriodValidation periodValidation;
    @InjectMocks
    private UniqueNameValidation uniqueNameValidation;

    @Test
    void conferenceExistValidationTest() {
        var conference = conference(3453535);
        Mockito.when(conferenceDao.existsById(conference.getId()))
                .thenReturn(Boolean.FALSE);

        checkThrowBy(() -> conferenceExistValidation.validate(conference));
    }

    @Test
    void periodValidationTest() {
        var conference = conference(1);
        Mockito.when(conferenceDao.checkOnExistPeriod(conference.getName(), conference.getDateStart(),
                conference.getDateEnd()))
                .thenReturn(1);

        checkThrowBy(() -> periodValidation.validate(conference));
    }

    @Test
    void uniqueNameValidationTest() {
        var conference = conference(123123);
        Mockito.when(conferenceDao.findByName(conference.getName()))
                .thenReturn(Optional.of(conference));

        checkThrowBy(() -> uniqueNameValidation.validate(conference));
    }

    private void checkThrowBy(ThrowableAssert.ThrowingCallable call) {
        Assertions.assertThatThrownBy(call)
                .isInstanceOf(ConferenceException.class);
    }


    private Conference conference(Integer id) {
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