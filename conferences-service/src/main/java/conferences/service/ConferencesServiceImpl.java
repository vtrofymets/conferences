package conferences.service;

import conferences.api.dto.Conference;
import conferences.dao.ConferenceDao;
import conferences.domain.ConferenceEntity;
import conferences.providers.ConferencesProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConferencesServiceImpl implements ConferencesService {

    private final ConferenceDao conferenceDao;
    private final ConferencesProvider provider;

    @Override
    @Transactional
    public int addNewConference(Conference conference) {
        var conferenceEntity = provider.apply(null, conference);
        return conferenceDao.save(conferenceEntity).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Conference> receiveAllConferences() {
        return conferenceDao.findAll().stream().map(ConferenceEntity::to).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateConference(Integer conferenceId, Conference conference) {
        var conferenceEntity = provider.apply(conferenceId, conference);
        conferenceDao.save(conferenceEntity);
    }
}
