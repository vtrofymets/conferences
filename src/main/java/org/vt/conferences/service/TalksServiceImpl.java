package org.vt.conferences.service;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vt.conferences.dao.TalksDao;
import org.vt.conferences.domain.Talk;
import org.vt.conferences.exceptions.TalkException;
import org.vt.conferences.service.validations.Validation;
import org.vt.conferences.service.validations.ValidationsService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TalksServiceImpl implements TalksService {

    private final TalksDao talksDao;
    private final ValidationsService validationsService;
    private final List<Validation<Talk>> talkValidations;

    @Override
    @Transactional
    public Talk addTalkToConference(@NonNull Talk talk) {
        validationsService.validation(talk, talkValidations);
        log.info("Add new talk=[{}], to conference=[{}]", talk, talk.getConferenceId());
        return talksDao.save(talk);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Talk> receiveConferenceTalks(@NonNull Long conferenceId) {
        log.info("Start to receive conference talks for conferenceId");
        return talksDao.findByConferenceId(conferenceId);
    }

    @Override
    @Transactional(readOnly = true)
    public Talk findTalk(@NonNull Long talkId) {
        log.info("Start to receive conference talks for conferenceId");
        return talksDao.findById(talkId)
                .orElseThrow(() -> new TalkException("Talk Not Found by id=[" + talkId + "]", HttpStatus.NOT_FOUND));
    }
}
