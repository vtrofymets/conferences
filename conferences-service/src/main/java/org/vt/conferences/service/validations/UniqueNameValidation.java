package org.vt.conferences.service.validations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.vt.conferences.dao.ConferenceDao;
import org.vt.conferences.domain.Conference;
import org.vt.conferences.exceptions.ConferenceException;

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