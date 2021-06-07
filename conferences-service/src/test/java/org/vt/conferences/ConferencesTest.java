package org.vt.conferences;

import com.fasterxml.jackson.databind.ObjectMapper;
import conferences.api.dto.ConferenceRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.vt.conferences.dao.ConferenceDao;
import org.vt.conferences.domain.Conference;
import org.vt.conferences.providers.ConferencesProvider;

import java.time.LocalDate;

@SpringBootTest(classes = ConferencesServiceApplication.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ConferencesTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ConferenceDao conferenceDao;
    @Autowired
    private ConferencesProvider conferencesProvider;

    @Test
    public void createConferenceStatus201() throws Exception {
        var request = new ConferenceRequest().name("TestConference")
                .topic("TestTopic")
                .dateStart(LocalDate.of(2021, 10, 10).toString())
                .dateEnd(LocalDate.of(2021, 10, 20).toString())
                .participants(500);

        var body = new ObjectMapper().writeValueAsString(request);

        String response = mockMvc.perform(
                MockMvcRequestBuilders.post("/conferences").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertNotNull(response);
        int id = Integer.parseInt(response);
        Assertions.assertTrue(id > 0);

        org.assertj.core.api.Assertions.assertThat(conferenceDao.findById(id).map(Conference::getName).orElse(null))
                .isEqualTo("TestConference");
    }

    @Test
    public void checkParticipantsLessThan100() throws Exception {
        var request = new ConferenceRequest().name("TestConference")
                .topic("TestTopic")
                .dateStart(LocalDate.of(2021, 10, 10).toString())
                .dateEnd(LocalDate.of(2021, 10, 20).toString())
                .participants(99);

        var body = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/conferences").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void checkConferenceUniqueName() throws Exception {
        var request = new ConferenceRequest().name("UniqueName")
                .topic("TestTopic")
                .dateStart(LocalDate.of(2022, 10, 10).toString())
                .dateEnd(LocalDate.of(2022, 10, 20).toString())
                .participants(500);

        Integer conferenceId = conferenceDao.save(conferencesProvider.apply(null, request)).getId();

        org.assertj.core.api.Assertions.assertThat(conferenceId).isNotNegative();

        var body = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/conferences").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void checkOnConferenceDatesPeriod() throws Exception {
        var anotherConf = conferenceDao.save(Conference.builder()
                .name("AnotherPeriod")
                .topic("AnotherTopic")
                .dateStart(LocalDate.of(2021, 6, 10))
                .dateEnd(LocalDate.of(2021, 6, 20))
                .participants(400)
                .build()).getId();

        org.assertj.core.api.Assertions.assertThat(anotherConf).isNotNull();

        LocalDate from = LocalDate.of(2021, 6, 5);
        LocalDate to = LocalDate.of(2021, 6, 15);

        var request = new ConferenceRequest().name("PeriodName")
                .topic("PeriodTopic")
                .dateStart(from.toString())
                .dateEnd(to.toString())
                .participants(500);

        var body = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/conferences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
