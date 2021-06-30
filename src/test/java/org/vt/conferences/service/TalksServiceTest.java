package org.vt.conferences.service;

import conferences.api.dto.TalkRequest;
import conferences.api.dto.TalkResponse;
import conferences.api.dto.TalkType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.vt.conferences.ConferencesServiceApplication;
import org.vt.conferences.dao.ConferenceDao;
import org.vt.conferences.dao.TalksDao;
import org.vt.conferences.domain.Talk;

import java.util.List;

/**
 * @author Vlad Trofymets
 */
@SpringBootTest(classes = ConferencesServiceApplication.class)
@ActiveProfiles("test")
public class TalksServiceTest {

    public static final String TITLE = "TITLE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String SPEAKER = "SPEAKER";
    @Autowired
    private TalksService talksService;
    @MockBean
    private TalksDao talksDao;
    @MockBean
    private ConferenceDao conferenceDao;

    @Test
    void addTalkToConferenceTest() {
        var talk = talk();

        var request = new TalkRequest().title(TITLE)
                .description(DESCRIPTION)
                .speaker(SPEAKER)
                .talkType(TalkType.MASTER_CLASS);

        talksService.addTalkToConference(1L, request);

        Mockito.verify(talksDao)
                .save(talk);
    }

    @Test
    void receiveConferenceTalksTest() {
        var id = 1L;
        var talk = talk();
        Mockito.when(talksDao.findByConferenceId(id))
                .thenReturn(List.of(talk));

        var actual = talksService.receiveConferenceTalks(id);

        Assertions.assertThat(actual)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .isEqualTo(List.of(new TalkResponse().title(TITLE)
                        .talkType(TalkType.MASTER_CLASS)
                        .speaker(SPEAKER)
                        .conferenceId(id)
                        .description(DESCRIPTION)));
    }

    private Talk talk() {
        return Talk.builder()
                .conferenceId(1L)
                .description(DESCRIPTION)
                .title(TITLE)
                .speaker(SPEAKER)
                .type(TalkType.MASTER_CLASS)
                .build();
    }


}
