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
public class SpeakerLimitTalkValidation implements Validation<Talk> {

    private final TalksDao talksDao;

    @Override
    public boolean validate(Talk talk) {
        if (talksDao.findByConferenceIdAndSpeaker(talk.getConferenceId(), talk.getSpeaker()).size() == 3) {
            throw new TalkException("3 Talks Limit", HttpStatus.CONFLICT);
        }
        return false;
    }
}
