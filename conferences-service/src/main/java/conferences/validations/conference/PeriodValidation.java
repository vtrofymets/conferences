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
public class PeriodValidation implements Validation<Conference> {

    private final ConferenceDao conferenceDao;

    @Override
    public boolean validate(Conference conference) {
        if (conferenceDao.checkOnExistPeriod(conference.getName(), conference.getDateStart(),
                conference.getDateEnd()) > 0) {
            throw new ConferenceException("Cant add Conference On this Dates!", HttpStatus.BAD_REQUEST);
        }
        return false;
    }
}
