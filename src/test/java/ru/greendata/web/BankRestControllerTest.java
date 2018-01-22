package ru.greendata.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ru.greendata.TestUtil;
import ru.greendata.model.Bank;
import ru.greendata.service.BankService;
import ru.greendata.web.json.JsonUtil;

import javax.annotation.PostConstruct;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.greendata.BankTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BankRestControllerTest {

    private static final String REST_URL = BankRestController.REST_URL + '/';

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    protected BankService bankService;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void testGet() throws Exception {
        System.out.println(REST_URL + SBER_ID);
        mockMvc.perform(get(REST_URL + SBER_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(SBER));
    }

    @Test
    public void testGetByBic() throws Exception {
        mockMvc.perform(get(REST_URL + "by?bic=" + SBER.getBic()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(SBER));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + SBER_ID))
                .andDo(print())
                .andExpect(status().isOk());
        assertMatch(bankService.getAll(), DEUTCHE);
    }

    @Test
    public void testUpdate() throws Exception {
        Bank updated = new Bank(SBER);
        updated.setName("UpdatedName");

        mockMvc.perform(put(REST_URL + SBER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(bankService.get(SBER_ID), updated);
    }

    @Test
    public void testCreate() throws Exception {
        Bank expected = new Bank(null, "Klukva", "045773790");
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Bank returned = TestUtil.readFromJson(action, Bank.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(bankService.getAll(), DEUTCHE, expected, SBER);
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DEUTCHE, SBER)));
    }
}
