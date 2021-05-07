package conferences.service.validations;

import conferences.dao.TalksDao;
import conferences.domain.Talk;
import conferences.exceptions.TalkException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * @author Vlad Trofymets on 06.05.2021
 */
@Service
@RequiredArgsConstructor
public class UniqueTitleValidation implements Validation<Talk> {

    private final TalksDao talksDao;

    @Override
    public void validate(Talk talk) {
        if (talksDao.existsByConferenceIdAndTitle(talk.getConferenceId(), talk.getTitle())) {
            throw new TalkException("Title already exist", HttpStatus.CONFLICT);
        }
    }
}