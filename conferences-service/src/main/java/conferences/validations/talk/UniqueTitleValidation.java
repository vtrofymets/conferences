package conferences.validations.talk;

import conferences.dao.TalksDao;
import conferences.domain.Talk;
import conferences.exceptions.TalkException;
import conferences.validations.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueTitleValidation implements Validation<Talk> {

    private final TalksDao talksDao;

    @Override
    public boolean validate(Talk talk) {
        if (talksDao.existsByConferenceIdAndTitle(talk.getConferenceId(), talk.getTitle())) {
            throw new TalkException("Title already exist", HttpStatus.CONFLICT);
        }
        return false;
    }
}
