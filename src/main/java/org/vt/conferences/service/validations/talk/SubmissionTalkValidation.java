package org.vt.conferences.service.validations.talk;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.vt.conferences.dao.ConferenceDao;
import org.vt.conferences.domain.Talk;
import org.vt.conferences.exceptions.TalkException;
import org.vt.conferences.service.validations.Validation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @author Vlad Trofymets
 */
@Service
@RequiredArgsConstructor
public class SubmissionTalkValidation implements Validation<Talk> {

    private final ConferenceDao conferenceDao;

    @Override
    public void validate(Talk talk) {
        if (conferenceDao.findById(talk.getConferenceId())
                .filter(e -> ChronoUnit.MONTHS.between(LocalDate.now(), e.getDateStart()) < 1)
                .isPresent()) {
            throw new TalkException("The Talk can be submitted 30 days in advance!", HttpStatus.BAD_REQUEST);
        }
    }

}

