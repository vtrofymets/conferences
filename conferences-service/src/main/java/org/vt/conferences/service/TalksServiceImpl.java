package org.vt.conferences.service;


import conferences.api.dto.TalkRequest;
import conferences.api.dto.TalkResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vt.conferences.dao.TalksDao;
import org.vt.conferences.domain.Talk;
import org.vt.conferences.mappers.TalksProvider;
import org.vt.conferences.service.validations.Validation;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TalksServiceImpl implements TalksService {

    private final TalksDao talksDao;
    private final TalksProvider provider;
    private final List<Validation<Talk>> talkValidations;

    @Override
    @Transactional
    public void addTalkToConference(Integer conferenceId, TalkRequest request) {
        var talk = provider.map(conferenceId, request);
        talkValidations.forEach(v -> v.validate(talk));
        log.info("Save = {}", talk);
        talksDao.save(talk);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TalkResponse> receiveConferenceTalks(Integer id) {
        return talksDao.findByConferenceId(id)
                .stream()
                .map(provider.toResponse())
                .collect(Collectors.toList());
    }
}
