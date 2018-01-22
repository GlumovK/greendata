package ru.greendata.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ru.greendata.ClientTestData;
import ru.greendata.TestUtil;
import ru.greendata.model.Client;
import ru.greendata.service.ClientService;
import ru.greendata.web.json.JsonUtil;

import javax.annotation.PostConstruct;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.greendata.ClientTestData.*;
import static ru.greendata.ClientTestData.TRUMP;
import static ru.greendata.model.LegalForm.FORM_PERSON;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class ClientRestControllerTest {

    private static final String REST_URL = ClientRestController.REST_URL + '/';

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    protected ClientService clientService;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + PUTIN_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(PUTIN));
    }

    @Test
    public void testGetByName() throws Exception {
        mockMvc.perform(get(REST_URL + "by?name=" + PUTIN.getName()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(ClientTestData.contentJson(PUTIN));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + PUTIN_ID))
                .andDo(print())
                .andExpect(status().isOk());
        assertMatch(clientService.getAll(), ROGA, TRUMP);
    }

    @Test
    public void testUpdate() throws Exception {
        Client updated = new Client(PUTIN);
        updated.setName("UpdatedName");

        mockMvc.perform(put(REST_URL + PUTIN_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(clientService.get(PUTIN_ID), updated);
    }

    @Test
    public void testCreate() throws Exception {
        Client expected = new Client(null, "Obama B.", "Obama", "USA, NY", FORM_PERSON);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Client returned = TestUtil.readFromJson(action, Client.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(clientService.getAll(), expected, PUTIN, ROGA, TRUMP);
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(PUTIN, ROGA, TRUMP)));
    }

}
