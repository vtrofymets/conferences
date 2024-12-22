package org.vt.conferences.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import conferences.api.dto.ErrorResponse;
import conferences.api.dto.TalkCreatedResponse;
import conferences.api.dto.TalkRequest;
import conferences.api.dto.TalkResponse;
import conferences.api.dto.TalkType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.vt.conferences.config.security.WebSecurityConfig;
import org.vt.conferences.domain.Talk;
import org.vt.conferences.mappers.TalkMapperImpl;
import org.vt.conferences.service.TalksService;

import java.util.List;
import java.util.stream.LongStream;

/**
 * @author Vlad Trofymets
 */
@WebMvcTest(controllers = TalksRestController.class)
@ActiveProfiles("test")
@Import(value = {TalkMapperImpl.class, WebSecurityConfig.class, WebEndpointProperties.class})
class TalksRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TalksService talksService;

    @Test
    void addTalkExpectedStatusCode200WithResponseBodyTest() throws Exception {
        var request = new TalkRequest().title("Java 100")
                .description("All about Java 100")
                .talkType(TalkType.MASTER_CLASS)
                .speaker("Person");

        var content = objectMapper.writeValueAsString(request);

        long talkId = 100L;
        Mockito.when(talksService.addTalkToConference(Mockito.any(Talk.class)))
                .thenAnswer(x -> {
                    var talk = (Talk) x.getArgument(0);
                    talk.setId(talkId);
                    return talk;
                });

        var responseContent = mockMvc.perform(MockMvcRequestBuilders.post("/conferences/{conferenceId}/talks", 1)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var result = objectMapper.readValue(responseContent, TalkCreatedResponse.class);
        Assertions.assertThat(result)
                .isNotNull()
                .extracting(TalkCreatedResponse::getId)
                .isEqualTo(talkId);
    }

    @Test
    void addTalkWithIdLessThanOneExpectedBadRequestTest() throws Exception {
        var responseContent = mockMvc.perform(MockMvcRequestBuilders.post("/conferences/{conferenceId}/talks", 0)
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
    void retrieveTalksByConferenceIdTest() throws Exception {
        var confId = 1L;
        var expected = LongStream.rangeClosed(1, 5)
                .mapToObj(i -> Talk.builder()
                        .id(i)
                        .conferenceId(confId)
                        .build())
                .toList();
        Mockito.when(talksService.receiveConferenceTalks(confId))
                .thenReturn(expected);

        var responseContent = mockMvc.perform(MockMvcRequestBuilders.get("/conferences/{conferenceId}/talks", confId))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var actual = List.of(objectMapper.readValue(responseContent, TalkResponse[].class));
        Assertions.assertThat(actual)
                .isNotEmpty()
                .hasSize(5)
                .extracting(TalkResponse::getId)
                .containsAll(expected.stream()
                        .map(Talk::getId)
                        .toList());
    }

}
