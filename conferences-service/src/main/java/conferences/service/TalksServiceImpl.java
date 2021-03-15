package conferences.service;

import conferences.api.dto.Talk;
import conferences.dao.TalksDao;
import conferences.domain.TalkEntity;
import conferences.providers.TalksProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TalksServiceImpl implements TalksService {

    private final TalksDao talksDao;
    private final TalksProvider provider;

    @Override
    @Transactional
    public void addNewTalkToConference(Integer conferenceId, Talk talk) {
        log.info("Add New Talk By CondId{} and Body{}", conferenceId, talk);
        var talkEntity = provider.apply(conferenceId, talk);
        talksDao.save(talkEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Talk> receiveAllTalksByConferenceId(Integer id) {
        return talksDao.findByConferenceId(id).stream().map(TalkEntity::to).collect(Collectors.toList());
    }
}
