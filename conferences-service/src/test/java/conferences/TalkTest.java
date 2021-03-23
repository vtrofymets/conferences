package conferences;

import conferences.dao.ConferenceDao;
import conferences.dao.TalksDao;
import conferences.providers.ConferencesProvider;
import conferences.providers.TalksProvider;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = ConferencesServiceApplication.class)
@RunWith(SpringRunner.class)
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

    @Before
    public void before() {

    }

}
