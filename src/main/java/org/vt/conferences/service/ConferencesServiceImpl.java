package org.vt.conferences.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vt.conferences.dao.ConferenceDao;
import org.vt.conferences.domain.Conference;
import org.vt.conferences.service.validations.Validation;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class ConferencesServiceImpl implements ConferencesService {

    private final ConferenceDao conferenceDao;
    private final ValidationsService validationsService;
    private final List<Validation<Conference>> conferenceValidations;

    @Override
    @Transactional
    public long addConference(Conference conference) {
        validationsService.validation(conference, conferenceValidations);
        log.info("Save = {}", conference);
        return conferenceDao.save(conference)
                .getId();
    }

    @Override
    @Transactional
    public void updateConference(Conference conference) {
        validationsService.validation(conference, conferenceValidations);
        log.info("Update = {}", conference);
        conferenceDao.save(conference);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Conference> receiveConferences(Boolean entirePeriod) {
        var conferences = entirePeriod ? conferenceDao.findAll() : conferenceDao.findAllActive();
        log.info("Find conferences[{}], entirePeriod[{}]", conferences.size(), entirePeriod);
        return conferences;
    }

}
