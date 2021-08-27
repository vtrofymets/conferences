package org.vt.conferences.service;


import org.vt.conferences.domain.Conference;

import java.util.List;

public interface ConferencesService {

    long addConference(Conference conference);

    void updateConference(Conference conference);

    List<Conference> receiveConferences(Boolean entirePeriod);

}
