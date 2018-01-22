package ru.greendata.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.greendata.model.Deposit;
import ru.greendata.util.exception.NotFoundException;

import static java.time.LocalDate.of;
import static ru.greendata.BankTestData.SBER_ID;
import static ru.greendata.ClientTestData.PUTIN_ID;
import static ru.greendata.DepositTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class DepositServiceTest {

    @Autowired
    private DepositService service;

    @Test
    public void delete() throws Exception {
        service.delete(DEPOSIT1_ID, PUTIN_ID, SBER_ID);
        assertMatch(service.getAll(), DEPOSIT2, DEPOSIT3, DEPOSIT4);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(DEPOSIT1_ID, 1, 2);
    }

    @Test
    public void create() throws Exception {
        Deposit newDeposit = new Deposit(null, of(2018, 01, 20), 6, 6);
        Deposit created = service.create(newDeposit, PUTIN_ID, SBER_ID);
        newDeposit.setId(created.getId());
        assertMatch(service.getAll(), DEPOSIT1, DEPOSIT2, DEPOSIT3, DEPOSIT4, newDeposit);
    }

    @Test
    public void update() throws Exception {
        Deposit updated = new Deposit(DEPOSIT1);
        updated.setPeriod(100);
        service.update(updated, PUTIN_ID, SBER_ID);
        assertMatch(service.get(DEPOSIT1_ID, PUTIN_ID, SBER_ID), updated);
    }

    @Test
    public void getByBank() throws Exception {
        assertMatch(service.getByBank(SBER_ID), DEPOSIT1, DEPOSIT3);
    }

    @Test
    public void getByClient() throws Exception {
        assertMatch(service.getByClient(PUTIN_ID), DEPOSIT1);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(), DEPOSITS);
    }

    @Test
    public void get() throws Exception {
        Deposit actual = service.get(DEPOSIT1_ID, PUTIN_ID, SBER_ID);
        assertMatch(actual, DEPOSIT1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1, PUTIN_ID, SBER_ID);
    }
}
