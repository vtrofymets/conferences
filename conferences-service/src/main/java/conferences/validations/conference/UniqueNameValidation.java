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
public class UniqueNameValidation implements Validation<Conference> {

    private final ConferenceDao conferenceDao;

    @Override
    public boolean validate(Conference conference) {
        if (conferenceDao.findByName(conference.getName()).isPresent()) {
            throw new ConferenceException("Conference With Name " + conference.getName() + " Already Exist!",
                    HttpStatus.CONFLICT);
        }
        return false;
    }
}
