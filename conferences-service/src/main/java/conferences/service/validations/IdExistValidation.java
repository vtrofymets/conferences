package conferences.service.validations;

/**
 * @author Vlad Trofymets on 06.05.2021
 */

import conferences.dao.ConferenceDao;
import conferences.domain.Conference;
import conferences.exceptions.ConferenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdExistValidation implements Validation<Conference> {

    private final ConferenceDao conferenceDao;

    @Override
    public void validate(Conference conference) {
        if (conference.getId() != null && !conferenceDao.existsById(conference.getId())) {
            throw new ConferenceException("Conference With Id " + conference.getId() + " Not Found",
                    HttpStatus.NOT_FOUND);
        }
    }
}

