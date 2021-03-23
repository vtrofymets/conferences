package conferences.service;


import conferences.api.dto.TalkRequest;
import conferences.api.dto.TalkResponse;
import conferences.dao.ConferenceDao;
import conferences.dao.TalksDao;
import conferences.exceptions.TalkException;
import conferences.providers.TalksProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TalksServiceImpl implements TalksService {

    private final TalksDao talksDao;
    private final TalksProvider provider;
    private final ConferenceDao conferenceDao;

    @Override
    @Transactional
    public void addTalkToConference(Integer conferenceId, TalkRequest request) {
        var talk = provider.apply(conferenceId, request);
        if (talksDao.findByConferenceIdAndSpeaker(talk.getConferenceId(), talk.getSpeaker()).size() == 3) {
            throw new TalkException("3 Talks Limit", HttpStatus.CONFLICT);
        } else if (conferenceDao.findById(talk.getConferenceId())
                .filter(e -> ChronoUnit.DAYS.between(LocalDate.now(), e.getDateStart()) < 30)
                .isPresent()) {
            throw new TalkException("The Talk can be submitted 30 days in advance!", HttpStatus.BAD_REQUEST);
        } else if (talksDao.existsByConferenceIdAndTitle(talk.getConferenceId(), talk.getTitle())) {
            throw new TalkException("Title already exist", HttpStatus.CONFLICT);
        }
        log.info("Save = {}", talk);
        talksDao.save(talk);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TalkResponse> receiveConferenceTalks(Integer id) {
        return talksDao.findByConferenceId(id).stream().map(provider.toResponse()).collect(Collectors.toList());
    }
}
