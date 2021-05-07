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
public class PeriodValidation implements Validation<Conference> {

    private final ConferenceDao conferenceDao;

    @Override
    public void validate(Conference conference) {
        if (conferenceDao.checkOnExistPeriod(conference.getName(), conference.getDateStart(),
                conference.getDateEnd()) > 0) {
            throw new ConferenceException("Cant add Conference On this Dates!", HttpStatus.BAD_REQUEST);
        }
    }
}

