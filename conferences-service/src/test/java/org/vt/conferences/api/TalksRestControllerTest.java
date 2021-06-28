package org.vt.conferences.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import conferences.api.dto.TalkRequest;
import conferences.api.dto.TalkType;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.vt.conferences.ConferencesServiceApplication;
import org.vt.conferences.service.TalksService;

/**
 * @author Vlad Trofymets
 */
@WebMvcTest(controllers = TalksRestController.class)
@ContextConfiguration(classes = ConferencesServiceApplication.class)
@ActiveProfiles("test")
class TalksRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TalksService talksService;

    @Test
    void addTalkTest() throws Exception {
        var request = new TalkRequest().title("Java 100")
                .description("All about Java 100")
                .talkType(TalkType.MASTER_CLASS)
                .speaker("Person");

        var content = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders.post("/conferences/{conferenceId}/talks", 1)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isCreated());
    }

    @Test
    void addTalkWithIdLessThan1ExpectedBadRequestTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/conferences/{conferenceId}/talks", 0)
                .content("ZXC")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status()
                        .isBadRequest());
    }

    @Test
    void retrieveTalksByConferenceIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/conferences/{conferenceId}/talks", 1))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk());
    }


}
