package conferences;

import com.fasterxml.jackson.databind.ObjectMapper;
import conferences.api.dto.ConferenceRequest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@SpringBootTest(classes = ConferencesServiceApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ExpConferencesServiceImplApplicationTests {

    @Autowired
    private MockMvc mockMvc;

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
        Assertions.assertTrue(Integer.parseInt(response) > 0);
    }

}
