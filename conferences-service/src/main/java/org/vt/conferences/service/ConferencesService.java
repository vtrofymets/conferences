package org.vt.conferences.service;


import conferences.api.dto.ConferenceRequest;
import conferences.api.dto.ConferenceResponse;

import java.util.List;

public interface ConferencesService {

    long addConference(ConferenceRequest conference);

    void updateConference(Long conferenceId, ConferenceRequest conference);

    List<ConferenceResponse> receiveConferences(Boolean entirePeriod);

}
