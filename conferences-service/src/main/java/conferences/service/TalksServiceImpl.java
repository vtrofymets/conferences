package conferences.service;

import conferences.api.dto.TalkDto;
import conferences.dao.ConferenceDao;
import conferences.dao.TalksDao;
import conferences.domain.Talk;
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
    private final ConferenceDao conferenceDao;
    private final TalksProvider provider;

    @Override
    @Transactional
    public void addNewTalkToConference(Integer conferenceId, TalkDto talk) {
        log.info("Add New Talk By CondId{} and Body{}", conferenceId, talk);
        if (talksDao.findBySpeaker(talk.getSpeaker()).size() == 3) {
            throw new TalkException("3 Talks Limit", HttpStatus.CONFLICT);
        } else if (conferenceDao.findById(conferenceId)
                .filter(e -> ChronoUnit.DAYS.between(e.getDateStart(), LocalDate.now()) < 30)
                .isPresent()) {
            throw new TalkException("The Talk can be submitted 30 days in advance!", HttpStatus.BAD_REQUEST);
        }
        var talkEntity = provider.apply(conferenceId, talk);
        talksDao.save(talkEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TalkDto> receiveAllTalksByConferenceId(Integer id) {
        return talksDao.findByConferenceId(id).stream().map(Talk::to).collect(Collectors.toList());
    }
}
