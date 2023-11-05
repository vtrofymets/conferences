package org.vt.conferences.api;


import conferences.api.ConferencesApi;
import conferences.api.dto.ConferenceCreatedResponse;
import conferences.api.dto.ConferenceRequest;
import conferences.api.dto.ConferenceResponse;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.vt.conferences.mappers.ConferenceMapper;
import org.vt.conferences.service.ConferencesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ConferencesRestController implements ConferencesApi {

    private final ConferenceMapper conferenceMapper;
    private final ConferencesService conferencesService;

    @Override
    @ResponseBody
    @Timed(value = "create_new_conference_time")
    @Counted(value = "create_new_conference_count")
    public ConferenceCreatedResponse createNewConference(@Valid ConferenceRequest request) {
        log.info("Create new conference request=[{}]", request);
        var conference = conferenceMapper.map(request);
        return new ConferenceCreatedResponse().id(conferencesService.saveConference(conference)
                .getId());
    }

    @Override
    @ResponseBody
    @Timed(value = "receive_available_conferences_time")
    @Counted(value = "receive_available_conferences_count")
    public List<ConferenceResponse> receiveAvailableConferences(Boolean entirePeriod) {
        log.info("Start to get conferences for entire period=[{}]", entirePeriod);
        var conferences = conferencesService.receiveConferences(entirePeriod);
        log.info("Received conferences=[{}]", conferences.size());
        return conferenceMapper.mapList(conferences);
    }

    @Override
    @ResponseBody
    @Timed(value = "receive_conference_by_conference_id_time")
    @Counted(value = "receive_conference_by_conference_id_count")
    public ConferenceResponse receiveConferenceByConferenceId(@Min(1L) Long conferenceId) {
        log.info("Start to get receiveConferenceByConferenceId=[{}]", conferenceId);
        return conferenceMapper.mapToConferenceResponse(conferencesService.receiveConferenceById(conferenceId));
    }

    @Override
    @Timed(value = "update_conference_time")
    @Counted(value = "update_conference_time")
    public void updateConference(@Min(1) Long conferenceId, @Valid ConferenceRequest request) {
        log.info("Update conference by conferenceId=[{}], request=[{}]", conferenceId, request);
        var conference = conferenceMapper.map(conferenceId, request);
        conferencesService.updateConference(conference);
    }
}
