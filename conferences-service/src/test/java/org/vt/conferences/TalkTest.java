package org.vt.conferences;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.vt.conferences.dao.ConferenceDao;
import org.vt.conferences.dao.TalksDao;
import org.vt.conferences.mappers.TalksProvider;

@SpringBootTest(classes = ConferencesServiceApplication.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class TalkTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ConferenceDao conferenceDao;
    @Autowired
    private TalksDao talksDao;
    @Autowired
    private TalksProvider conferencesProvider;

    @BeforeAll
    public void before() {

    }

}
