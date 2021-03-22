package conferences.validations.conference;

import conferences.dao.ConferenceDao;
import conferences.domain.Conference;
import conferences.exceptions.ConferenceException;
import conferences.validations.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistIdValidation implements Validation<Conference> {

    private final ConferenceDao conferenceDao;

    @Override
    public boolean validate(Conference conference) {
        if(conference.getId() != null && !conferenceDao.existsById(conference.getId())) {
            throw new ConferenceException("Conference With Id " + conference.getId() + " Not Found",
                    HttpStatus.NOT_FOUND);
        }
        return false;
    }
}
