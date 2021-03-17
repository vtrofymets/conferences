package conferences.service;

import conferences.api.dto.ConferenceDto;
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
    public int addNewConference(ConferenceDto conference) {
        log.info("Add Conference Body Request{}", conference);
        if (conferenceDao.findByName(conference.getName()).isPresent()) {
            throw new ConferenceException("Conference With Name " + conference.getName() + " Already Exist!",
                    HttpStatus.CONFLICT);
        } else if (conferenceDao.existBetweenDateStartAndDateEnd(conference.getDateStart(), conference.getDateEnd())) {
            throw new ConferenceException("Conference On Date " + conference.getDateStart() + " Already Exist!",
                    HttpStatus.BAD_REQUEST);
        }
        var conferenceEntity = provider.apply(null, conference);
        return conferenceDao.save(conferenceEntity).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConferenceDto> receiveAllConferences() {
        return conferenceDao.findAll().stream().map(Conference::to).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateConference(Integer conferenceId, ConferenceDto conference) {
        log.info("Update Conference By conferenceId{}, body{}", conferenceId, conference);
        var entity = conferenceDao.findById(conferenceId)
                .orElseThrow(() -> new ConferenceException("Conference With Id " + conferenceId + " Not Found",
                        HttpStatus.NOT_FOUND));
        var conferenceEntity = provider.apply(entity.getId(), conference);
        conferenceDao.save(conferenceEntity);
    }
}
