package conferences.service.validations;

import conferences.dao.ConferenceDao;
import conferences.domain.Conference;
import conferences.exceptions.ConferenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * @author Vlad Trofymets on 06.05.2021
 */
@Service
@RequiredArgsConstructor
public class UniqueNameValidation implements Validation<Conference> {

    private final ConferenceDao conferenceDao;

    @Override
    public void validate(Conference conference) {
        if (conferenceDao.findByName(conference.getName())
                .isPresent()) {
            throw new ConferenceException("Conference With Name " + conference.getName() + " Already Exist!",
                    HttpStatus.CONFLICT);
        }
    }
}