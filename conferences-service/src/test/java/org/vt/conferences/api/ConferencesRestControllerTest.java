package org.vt.conferences.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import conferences.api.dto.ConferenceCreatedResponse;
import conferences.api.dto.ConferenceRequest;
import conferences.api.dto.ConferenceResponse;
import conferences.api.dto.ErrorResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.vt.conferences.config.security.WebSecurityConfig;
import org.vt.conferences.domain.Conference;
import org.vt.conferences.exceptions.ConferenceException;
import org.vt.conferences.mappers.ConferenceMapperImpl;
import org.vt.conferences.service.ConferencesService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.LongStream;

/**
 * @author Vlad Trofymets
 */
@WebMvcTest(controllers = ConferencesRestController.class)
@Import(value = {ConferenceMapperImpl.class, WebSecurityConfig.class, WebEndpointProperties.class})
@ActiveProfiles("test")
class ConferencesRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ConferencesService conferencesService;

    @Test
    void addNewConferenceExpectedCreatedNewConferenceTest() throws Exception {
        var request = new ConferenceRequest().name("Conf1")
                .topic("Java")
                .participants(101)
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now()
                        .plusDays(3));
        long ID = 1L;
        Mockito.when(conferencesService.saveConference(Mockito.any()))
                .thenReturn(Conference.builder()
                        .id(ID)
                        .build());

        var content = objectMapper.writeValueAsString(request);

        var responseAsString = mockMvc.perform(MockMvcRequestBuilders.post("/conferences")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertThat(objectMapper.readValue(responseAsString, ConferenceCreatedResponse.class))
                .isNotNull()
                .extracting(ConferenceCreatedResponse::getId)
                .isEqualTo(ID);
    }

    @Test
    void addNewConferenceWithLessThen100ParticipantsExpectedBadRequestTest() throws Exception {
        var request = new ConferenceRequest().name("Test1")
                .topic("Java")
                .participants(99)
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now()
                        .plusDays(3));

        var content = objectMapper.writeValueAsString(request);

        var responseContent = mockMvc.perform(MockMvcRequestBuilders.post("/conferences")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var errorResponse = objectMapper.readValue(responseContent, ErrorResponse.class);
        Assertions.assertThat(errorResponse.getErrorMessage())
                .isNotEmpty()
                .contains("must be greater than or equal to 101");
    }

    @Test
    void addNewConferenceWithEmptyNameAndTopicExpectedBadRequestTest() throws Exception {
        var request = new ConferenceRequest().participants(111)
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now()
                        .plusDays(3));

        var content = objectMapper.writeValueAsString(request);

        var responseContent = mockMvc.perform(MockMvcRequestBuilders.post("/conferences")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var errorResponse = objectMapper.readValue(responseContent, ErrorResponse.class);
        Assertions.assertThat(errorResponse.getErrorMessage())
                .isNotEmpty();
    }

    @Test
    void receiveAllConferencesTest() throws Exception {
        Mockito.when(conferencesService.receiveConferences(Boolean.FALSE))
                .thenReturn(List.of(Conference.builder()
                        .dateStart(LocalDate.now())
                        .dateEnd(LocalDate.now())
                        .build()));

        mockMvc.perform(MockMvcRequestBuilders.get("/conferences"))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());
    }

    @Test
    void receiveAllConferencesWithEntirePeriodTrueTest() throws Exception {
        var expected = LongStream.rangeClosed(1, 10)
                .mapToObj(i -> Conference.builder()
                        .id(i)
                        .dateStart(LocalDate.now())
                        .dateEnd(LocalDate.now())
                        .build())
                .toList();
        Mockito.when(conferencesService.receiveConferences(Boolean.TRUE))
                .thenReturn(expected);

        String contentAsString = mockMvc.perform(MockMvcRequestBuilders.get("/conferences")
                        .queryParam("entirePeriod", Boolean.TRUE.toString()))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var actual = List.of(objectMapper.readValue(contentAsString, ConferenceResponse[].class));
        Assertions.assertThat(actual)
                .isNotEmpty()
                .hasSize(10)
                .extracting(ConferenceResponse::getId)
                .containsAll(expected.stream()
                        .map(Conference::getId)
                        .toList());
    }

    @Test
    void updateConferenceTest() throws Exception {
        var request = new ConferenceRequest().name("Test1")
                .topic("Java")
                .participants(101)
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now()
                        .plusDays(3));

        var content = objectMapper.writeValueAsString(request);

        Mockito.doNothing()
                .when(conferencesService)
                .updateConference(Mockito.any());

        mockMvc.perform(MockMvcRequestBuilders.put("/conferences/{conferenceId}", 1)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isNoContent());
    }

    @Test
    void updateConferenceWithIdLessThanOneExpectedBadRequestTest() throws Exception {
        var responseContent = mockMvc.perform(MockMvcRequestBuilders.put("/conferences/{conferenceId}", 0)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();


        var errorResponse = objectMapper.readValue(responseContent, ErrorResponse.class);
        Assertions.assertThat(errorResponse.getErrorMessage())
                .isNotEmpty();
    }

    @Test
    void updateConferenceExpectedNotFoundTest() throws Exception {
        var request = new ConferenceRequest().name("Test1")
                .topic("Java")
                .participants(101)
                .dateStart(LocalDate.now())
                .dateEnd(LocalDate.now()
                        .plusDays(3));

        var content = objectMapper.writeValueAsString(request);

        var errorMessage = "Conference With Id[" + 2342423 + "] Not Found";
        Mockito.doThrow(new ConferenceException(errorMessage, HttpStatus.NOT_FOUND))
                .when(conferencesService)
                .updateConference(Mockito.any());

        var responseContent = mockMvc.perform(MockMvcRequestBuilders.put("/conferences/{conferenceId}", 2342423)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertThat(objectMapper.readValue(responseContent, ErrorResponse.class))
                .isNotNull()
                .extracting(ErrorResponse::getErrorMessage)
                .isEqualTo(errorMessage);
    }

}
