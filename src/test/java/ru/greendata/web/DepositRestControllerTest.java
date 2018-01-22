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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ru.greendata.DepositTestData;
import ru.greendata.TestUtil;
import ru.greendata.model.Deposit;
import ru.greendata.service.DepositService;
import ru.greendata.web.json.JsonUtil;

import javax.annotation.PostConstruct;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.greendata.BankTestData.SBER_ID;
import static ru.greendata.ClientTestData.*;
import static ru.greendata.DepositTestData.*;
import static ru.greendata.DepositTestData.assertMatch;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class DepositRestControllerTest {

    private static final String REST_URL = DepositRestController.REST_URL + '/';

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    protected DepositService depositService;

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + DEPOSIT1_ID + "/" + PUTIN_ID + "/" + SBER_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DEPOSIT1));
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(DEPOSIT1, DEPOSIT2, DEPOSIT3, DEPOSIT4)));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + DEPOSIT1_ID + "/" + PUTIN_ID + "/" + SBER_ID))
                .andDo(print())
                .andExpect(status().isOk());
        assertMatch(depositService.getAll(), DEPOSIT2, DEPOSIT3, DEPOSIT4);
    }

    @Test
    public void testUpdate() throws Exception {
        Deposit updated = new Deposit(DEPOSIT1);
        updated.setPeriod(100);

        mockMvc.perform(put(REST_URL + DEPOSIT1_ID + "/" + PUTIN_ID + "/" + SBER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        assertMatch(depositService.get(DEPOSIT1_ID, PUTIN_ID, SBER_ID), updated);
    }


    @Test
    public void getByBank() throws Exception {
        mockMvc.perform(get(REST_URL + "by?bankId=" + SBER_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DepositTestData.contentJson(DEPOSIT1, DEPOSIT3));

        assertMatch(depositService.getByBank(SBER_ID), DEPOSIT1, DEPOSIT3);
    }
//    @Test
//    public void getByClient() throws Exception {
//        mockMvc.perform(get(REST_URL + "by?clientId=" + PUTIN_ID))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(DepositTestData.contentJson(DEPOSIT1));
//
//        assertMatch(depositService.getByClient(PUTIN_ID), DEPOSIT1);
//    }

}
