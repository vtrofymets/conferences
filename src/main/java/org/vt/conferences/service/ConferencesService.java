package org.vt.conferences.service;


import lombok.NonNull;
import org.vt.conferences.domain.Conference;

import java.util.List;

public interface ConferencesService {

    Conference saveConference(@NonNull Conference conference);

    void updateConference(@NonNull Conference conference);

    List<Conference> receiveConferences(boolean entirePeriod);

    Conference receiveConferenceById(@NonNull Long conferenceId);
}
