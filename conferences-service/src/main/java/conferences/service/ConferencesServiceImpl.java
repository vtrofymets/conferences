package conferences.service;


import conferences.api.dto.ConferenceRequest;
import conferences.api.dto.ConferenceResponse;
import conferences.dao.ConferenceDao;
import conferences.domain.Conference;
import conferences.exceptions.ConferenceException;
import conferences.providers.ConferencesProvider;
import conferences.validations.Validation;
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
    public int addConference(ConferenceRequest request) {
        var conference = provider.apply(null, request);
        validation(conference);
        log.info("Save = {}", conference);
        return conferenceDao.save(conference).getId();
    }

    @Override
    @Transactional
    public void updateConference(Integer conferenceId, ConferenceRequest request) {
        var conference = provider.apply(conferenceId, request);
        validation(conference);
        log.info("Update = {}", conference);
        conferenceDao.save(conference);
    }

    private void validation(Conference conference) {
        if (conference.getId() != null && !conferenceDao.existsById(conference.getId())) {
            throw new ConferenceException("Conference With Id " + conference.getId() + " Not Found",
                    HttpStatus.NOT_FOUND);
        }  else if (conferenceDao.findByName(conference.getName()).isPresent()) {
            throw new ConferenceException("Conference With Name " + conference.getName() + " Already Exist!",
                    HttpStatus.CONFLICT);
        } else if (conferenceDao.checkOnExistPeriod(conference.getName(), conference.getDateStart(),
                conference.getDateEnd()) > 0) {
            throw new ConferenceException("Cant add Conference On this Dates!", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConferenceResponse> receiveAllConferences() {
        return conferenceDao.findAll().stream().map(provider.toResponse()).collect(Collectors.toList());
    }
}
