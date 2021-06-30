package org.vt.conferences.service.validations;

/**
 * @author Vlad Trofymets on 06.05.2021
 */

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.vt.conferences.dao.ConferenceDao;
import org.vt.conferences.domain.Conference;
import org.vt.conferences.exceptions.ConferenceException;

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

