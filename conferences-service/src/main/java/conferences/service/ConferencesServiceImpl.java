package conferences.service;

import conferences.api.dto.ConferenceRequest;
import conferences.api.dto.ConferenceResponse;
import conferences.dao.ConferenceDao;
import conferences.domain.Conference;
import conferences.exceptions.ConferenceException;
import conferences.providers.ConferencesProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public int addNewConference(ConferenceRequest conference) {
        var conferenceEntity = provider.apply(null, conference);
        log.info("Entity is{}", conferenceEntity);
        if (conferenceDao.findByName(conferenceEntity.getName()).isPresent()) {
            throw new ConferenceException("Conference With Name " + conference.getName() + " Already Exist!",
                    HttpStatus.CONFLICT);
        } else if (conferenceDao.checkOnExistPeriod(conferenceEntity.getDateStart(), conferenceEntity.getDateEnd()) > 0) {
            throw new ConferenceException("Cant add Conference On this Dates!",
                    HttpStatus.BAD_REQUEST);
        }
        return conferenceDao.save(conferenceEntity).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConferenceResponse> receiveAllConferences() {
        return conferenceDao.findAll().stream().map(Conference::to).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateConference(Integer conferenceId, ConferenceRequest conference) {
        var entity = conferenceDao.findById(conferenceId)
                .orElseThrow(() -> new ConferenceException("Conference With Id " + conferenceId + " Not Found",
                        HttpStatus.NOT_FOUND));
        var conferenceEntity = provider.apply(entity.getId(), conference);
        conferenceDao.save(conferenceEntity);
    }
}
