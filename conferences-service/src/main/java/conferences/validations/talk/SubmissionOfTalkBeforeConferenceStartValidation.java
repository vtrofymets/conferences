package conferences.validations.talk;

import conferences.dao.ConferenceDao;
import conferences.domain.Talk;
import conferences.exceptions.TalkException;
import conferences.validations.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


@Component
@RequiredArgsConstructor
public class SubmissionOfTalkBeforeConferenceStartValidation implements Validation<Talk> {

    private final ConferenceDao conferenceDao;

    @Override
    public boolean validate(Talk talk) {
        if (conferenceDao.findById(talk.getConferenceId())
                .filter(e -> ChronoUnit.DAYS.between(LocalDate.now(), e.getDateStart()) < 30)
                .isPresent()) {
            throw new TalkException("The Talk can be submitted 30 days in advance!", HttpStatus.BAD_REQUEST);
        }
        return false;
    }
}
