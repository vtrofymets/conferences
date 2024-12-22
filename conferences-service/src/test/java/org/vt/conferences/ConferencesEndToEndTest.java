package org.vt.conferences;

import com.fasterxml.jackson.databind.ObjectMapper;
import conferences.api.dto.ConferenceCreatedResponse;
import conferences.api.dto.ConferenceRequest;
import conferences.api.dto.ErrorResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.vt.conferences.dao.ConferenceDao;
import org.vt.conferences.domain.Conference;
import org.vt.conferences.mappers.ConferenceMapper;

import java.time.LocalDate;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ConferencesEndToEndTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ConferenceDao conferenceDao;
    @Autowired
    private ConferenceMapper conferenceMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createConferenceStatus201() throws Exception {
        var request = new ConferenceRequest().name("TestConference")
                .topic("TestTopic")
                .dateStart(LocalDate.of(2021, 10, 10))
                .dateEnd(LocalDate.of(2021, 10, 20))
                .participants(500);

        var response = mockMvc.perform(MockMvcRequestBuilders.post("/conferences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status()
                        .isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var createdResponse = objectMapper.readValue(response, ConferenceCreatedResponse.class);
        Assertions.assertThat(createdResponse)
                .extracting(ConferenceCreatedResponse::getId)
                .isNotNull();

        Assertions.assertThat(conferenceDao.findById(createdResponse.getId())
                        .map(Conference::getName)
                        .orElse(null))
                .isEqualTo("TestConference");
    }

    @Test
    void checkConferenceUniqueName() throws Exception {
        var request = new ConferenceRequest().name("UniqueName")
                .topic("TestTopic")
                .dateStart(LocalDate.of(2022, 10, 10))
                .dateEnd(LocalDate.of(2022, 10, 20))
                .participants(500);

        var conferenceId = conferenceDao.save(conferenceMapper.map(null, request))
                .getId();

        Assertions.assertThat(conferenceId)
                .isNotNegative();

        var responseContent = mockMvc.perform(MockMvcRequestBuilders.post("/conferences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status()
                        .isConflict())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var errorResponse = objectMapper.readValue(responseContent, ErrorResponse.class);
        Assertions.assertThat(errorResponse)
                .isNotNull();
        Assertions.assertThat(errorResponse.getErrorMessage())
                .isNotEmpty()
                .isEqualTo("Conference with name=[UniqueName] already exist!");
    }

    @Test
    void checkOnConferenceDatesPeriod() throws Exception {
        var anotherConf = conferenceDao.save(Conference.builder()
                        .name("AnotherPeriod")
                        .topic("AnotherTopic")
                        .dateStart(LocalDate.of(2021, 6, 10))
                        .dateEnd(LocalDate.of(2021, 6, 20))
                        .participants(400)
                        .build())
                .getId();

        Assertions.assertThat(anotherConf)
                .isNotNull();

        var from = LocalDate.of(2021, 6, 5);
        var to = LocalDate.of(2021, 6, 15);

        var request = new ConferenceRequest().name("PeriodName")
                .topic("PeriodTopic")
                .dateStart(from)
                .dateEnd(to)
                .participants(500);

        var body = objectMapper.writeValueAsString(request);

        var responseContent = mockMvc.perform(MockMvcRequestBuilders.post("/conferences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var errorResponse = objectMapper.readValue(responseContent, ErrorResponse.class);
        Assertions.assertThat(errorResponse)
                .isNotNull();
        Assertions.assertThat(errorResponse.getErrorMessage())
                .isNotEmpty()
                .isEqualTo("Can't add Conference on this dates!");
    }
}
