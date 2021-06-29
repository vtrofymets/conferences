package org.vt.conferences.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import conferences.api.dto.ConferenceRequest;
import conferences.api.dto.ConferenceResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.vt.conferences.ConferencesServiceApplication;
import org.vt.conferences.exceptions.ConferenceException;
import org.vt.conferences.service.ConferencesService;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Vlad Trofymets on 07.05.2021
 */
@WebMvcTest(controllers = ConferencesRestController.class)
@ContextConfiguration(classes = ConferencesServiceApplication.class)
@ActiveProfiles("test")
class ConferencesRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ConferencesService conferencesService;

    @Test
    void addNewConferenceExpectedCreatedTest() throws Exception {
        var request = new ConferenceRequest().name("Test1")
                .topic("Java")
                .participants(101)
                .dateStart(LocalDate.now()
                        .toString())
                .dateEnd(LocalDate.now()
                        .plusDays(3)
                        .toString());
        Mockito.when(conferencesService.addConference(request))
                .thenReturn(1);

        var content = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/conferences")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isCreated());
    }

    @Test
    void addNewConferenceWithLessThen100ParticipantsExpectedBadRequestTest() throws Exception {
        var request = new ConferenceRequest().name("Test1")
                .topic("Java")
                .participants(99)
                .dateStart(LocalDate.now()
                        .toString())
                .dateEnd(LocalDate.now()
                        .plusDays(3)
                        .toString());
        Mockito.when(conferencesService.addConference(request))
                .thenReturn(1);

        var content = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/conferences")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());
    }

    @Test
    void addNewConferenceWithEmptyNameAndTopicExpectedBadRequestTest() throws Exception {
        var request = new ConferenceRequest().participants(111)
                .dateStart(LocalDate.now()
                        .toString())
                .dateEnd(LocalDate.now()
                        .plusDays(3)
                        .toString());
        Mockito.when(conferencesService.addConference(request))
                .thenReturn(1);

        var content = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/conferences")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());
    }

    @Test
    void receiveAllConferencesTest() throws Exception {
        Mockito.when(conferencesService.receiveConferences(Boolean.FALSE))
                .thenReturn(List.of(new ConferenceResponse()));

        mockMvc.perform(MockMvcRequestBuilders.get("/conferences"))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());
    }

    @Test
    void receiveAllConferencesWithEntirePeriodTrueTest() throws Exception {
        Mockito.when(conferencesService.receiveConferences(Boolean.TRUE))
                .thenReturn(List.of(new ConferenceResponse()));

        mockMvc.perform(MockMvcRequestBuilders.get("/conferences")
                .queryParam("entirePeriod", Boolean.TRUE.toString()))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());
    }

    @Test
    void updateConferenceTest() throws Exception {
        var request = new ConferenceRequest().name("Test1")
                .topic("Java")
                .participants(101)
                .dateStart(LocalDate.now()
                        .toString())
                .dateEnd(LocalDate.now()
                        .plusDays(3)
                        .toString());

        var content = objectMapper.writeValueAsString(request);

        Mockito.doNothing()
                .when(conferencesService)
                .updateConference(1, request);

        mockMvc.perform(MockMvcRequestBuilders.put("/conferences/{conferenceId}", 1)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isNoContent());
    }

    @Test
    void updateConferenceWithIdLessThan1ExpectedBadRequestTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/conferences/{conferenceId}", 0)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());
    }

    @Test
    void updateConferenceExpectedNotFoundTest() throws Exception {
        var request = new ConferenceRequest().name("Test1")
                .topic("Java")
                .participants(101)
                .dateStart(LocalDate.now()
                        .toString())
                .dateEnd(LocalDate.now()
                        .plusDays(3)
                        .toString());

        var content = objectMapper.writeValueAsString(request);

        Mockito.doThrow(new ConferenceException("Conference With Id[" + 2342423 + "] Not Found", HttpStatus.NOT_FOUND))
                .when(conferencesService)
                .updateConference(2342423, request);

        mockMvc.perform(MockMvcRequestBuilders.put("/conferences/{conferenceId}", 2342423)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isNotFound());
    }


}
