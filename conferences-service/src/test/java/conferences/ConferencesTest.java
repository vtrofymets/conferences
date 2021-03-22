package conferences;

import com.fasterxml.jackson.databind.ObjectMapper;
import conferences.api.dto.ConferenceRequest;
import conferences.dao.ConferenceDao;
import conferences.domain.Conference;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest(classes = ConferencesServiceApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ConferencesTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ConferenceDao conferenceDao;

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

        org.assertj.core.api.Assertions.assertThat(conferenceDao.findById(id))
                .map(Conference::getName)
                .isEqualTo("TestConference");

        conferenceDao.deleteById(id);
    }

    @Test
    public void checkParticipantsLessThan100() throws Exception {
        var request = new ConferenceRequest().name("TestConference")
                .topic("TestTopic")
                .dateStart(LocalDate.of(2021, 10, 10).toString())
                .dateEnd(LocalDate.of(2021, 10, 20).toString())
                .participants(500);

        var body = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/conferences").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void checkConferenceUniqueName() throws Exception {
        var request = new ConferenceRequest().name("UniqueName")
                .topic("TestTopic")
                .dateStart(LocalDate.of(2021, 10, 10).toString())
                .dateEnd(LocalDate.of(2021, 10, 20).toString())
                .participants(500);

        var body = new ObjectMapper().writeValueAsString(request);

        Mockito.when(conferenceDao.findByName("UniqueName")).thenReturn(Optional.of(new Conference()));

        mockMvc.perform(
                MockMvcRequestBuilders.post("/conferences").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void checkOnConferenceDatesPeriod() throws Exception {
        LocalDate from = LocalDate.of(2021, 10, 5);
        LocalDate to = LocalDate.of(2021, 10, 15);
        var request = new ConferenceRequest().name("UniqueName")
                .topic("TestTopic")
                .dateStart(from.toString())
                .dateEnd(to.toString())
                .participants(500);

        var body = new ObjectMapper().writeValueAsString(request);

        Mockito.when(conferenceDao.checkOnExistPeriod(request.getName(),from, to)).thenReturn(1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/conferences").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
