package org.vt.conferences.service;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vt.conferences.dao.ConferenceDao;
import org.vt.conferences.domain.Conference;
import org.vt.conferences.exceptions.ConferenceException;
import org.vt.conferences.service.validations.Validation;
import org.vt.conferences.service.validations.ValidationsService;

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
    public Conference saveConference(@NonNull Conference conference) {
        return saveOrUpdateConference(conference);
    }

    @Override
    @Transactional
    public void updateConference(@NonNull Conference conference) {
        saveOrUpdateConference(conference);
    }

    private Conference saveOrUpdateConference(@NonNull Conference conference) {
        validationsService.validation(conference, conferenceValidations);
        log.info("Save or update conference=[{}]", conference);
        return conferenceDao.save(conference);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Conference> receiveConferences(boolean entirePeriod) {
        var conferences = entirePeriod ? conferenceDao.findAll() : conferenceDao.findAllActive();
        log.info("Find conferences[{}], entirePeriod[{}]", conferences.size(), entirePeriod);
        return conferences;
    }

    @Override
    @Transactional(readOnly = true)
    public Conference receiveConferenceById(@NonNull Long conferenceId) {
        log.info("Start to receiveConferenceById=[{}]", conferenceId);
        return conferenceDao.findById(conferenceId)
                .orElseThrow(() -> new ConferenceException("Conference Not Found by id=[" + conferenceId + "]",
                        HttpStatus.NOT_FOUND));
    }

}
