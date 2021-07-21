package org.vt.conferences.service.validations;

import conferences.api.dto.TalkType;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.vt.conferences.dao.ConferenceDao;
import org.vt.conferences.dao.TalksDao;
import org.vt.conferences.domain.Conference;
import org.vt.conferences.domain.Talk;
import org.vt.conferences.exceptions.TalkException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Vlad Trofymets
 */
@ExtendWith(MockitoExtension.class)
public class TalksValidationTest {

    public static final long ID = 1L;
    @Mock
    private ConferenceDao conferenceDao;
    @Mock
    private TalksDao talksDao;
    @InjectMocks
    private SubmissionValidation submissionValidation;
    @InjectMocks
    private SpeakerLimitValidation speakerLimitValidation;
    @InjectMocks
    private UniqueTitleValidation uniqueTitleValidation;

    @Test
    void submissionValidationTest() {
        var talk = talk();
        Mockito.when(conferenceDao.findById(ID))
                .thenReturn(Optional.of(conference()));

        checkThrowBy(() -> submissionValidation.validate(talk));
    }

    @Test
    void submissionValidationSuccessTest() {
        var talk = talk();
        Mockito.when(conferenceDao.findById(ID))
                .thenReturn(Optional.of(conference2()));

        submissionValidation.validate(talk);
    }

    @Test
    void speakerLimitValidationTest() {
        var talk = talk();
        Mockito.when(talksDao.findByConferenceIdAndSpeaker(talk.getId(), talk.getSpeaker()))
                .thenReturn(List.of(talk, talk, talk));

        checkThrowBy(() -> speakerLimitValidation.validate(talk));
    }

    @Test
    void uniqueTitleValidationTest() {
        var talk = talk();
        Mockito.when(talksDao.existsByConferenceIdAndTitle(talk.getId(), talk.getTitle()))
                .thenReturn(Boolean.TRUE);

        checkThrowBy(() -> uniqueTitleValidation.validate(talk));
    }

    private void checkThrowBy(ThrowableAssert.ThrowingCallable call) {
        Assertions.assertThatThrownBy(call)
                .isInstanceOf(TalkException.class);
    }

    private Talk talk() {
        return Talk.builder()
                .id(ID)
                .conferenceId(ID)
                .description("Description")
                .speaker("Speaker")
                .type(TalkType.MASTER_CLASS)
                .title("Title")
                .build();
    }

    private Conference conference() {
        return Conference.builder()
                .id(ID)
                .name("NAME")
                .topic("TOPIC")
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now()
                        .plusDays(1))
                .participants(111)
                .build();
    }

    private Conference conference2() {
        return Conference.builder()
                .id(ID)
                .name("NAME")
                .topic("TOPIC")
                .dateStart(LocalDate.now().plusMonths(1))
                .dateEnd(LocalDate.now()
                        .plusMonths(2))
                .participants(111)
                .build();
    }
}
