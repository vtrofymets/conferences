package org.vt.conferences.service.validations.conference;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.vt.conferences.dao.ConferenceDao;
import org.vt.conferences.domain.Conference;
import org.vt.conferences.exceptions.ConferenceException;
import org.vt.conferences.service.validations.Validation;

/**
 * @author Vlad Trofymets
 */
@Service
@RequiredArgsConstructor
public class ExistConferenceValidation implements Validation<Conference> {

    private final ConferenceDao conferenceDao;

    @Override
    public void validate(Conference conference) {
        if (conference.getId() != null && !conferenceDao.existsById(conference.getId())) {
            throw new ConferenceException("Conference with id=[" + conference.getId() + "] Not Found",
                    HttpStatus.NOT_FOUND);
        }
    }
}

