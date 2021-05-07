package conferences.service.validations;

import conferences.dao.ConferenceDao;
import conferences.domain.Talk;
import conferences.exceptions.TalkException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @author Vlad Trofymets on 06.05.2021
 */
@Service
@RequiredArgsConstructor
public class SubmissionValidation implements Validation<Talk> {

    private final ConferenceDao conferenceDao;

    @Override
    public void validate(Talk talk) {
        if (conferenceDao.findById(talk.getConferenceId())
                .filter(e -> ChronoUnit.DAYS.between(LocalDate.now(), e.getDateStart()) < 30)
                .isPresent()) {
            throw new TalkException("The Talk can be submitted 30 days in advance!", HttpStatus.BAD_REQUEST);
        }
    }
}

