package ru.greendata.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.greendata.BankTestData;
import ru.greendata.model.Bank;
import ru.greendata.util.exception.NotFoundException;

import java.util.List;

import static ru.greendata.BankTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class BankServiceTest {

    @Autowired
    private BankService service;

    @Test
    public void create() throws Exception {
        Bank newBank = new Bank(null, "Klukva", "045773790");
        Bank created = service.create(newBank);
        newBank.setId(created.getId());
        assertMatch(service.getAll(), DEUTCHE, newBank, SBER);
    }

    @Test
    public void delete() throws Exception {
        service.delete(SBER_ID);
        BankTestData.assertMatch(service.getAll(), DEUTCHE);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void get() throws Exception {
        Bank bank = service.get(SBER_ID);
        assertMatch(bank, SBER);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void getByBic() throws Exception {
        Bank bank = service.getByBic("045773603");
        assertMatch(bank, SBER);
    }

    @Test
    public void update() throws Exception {
        Bank updated = new Bank(SBER);
        updated.setName("UpdatedName");
        service.update(updated);
        assertMatch(service.get(SBER_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<Bank> all = service.getAll();
        assertMatch(all, DEUTCHE, SBER);
    }
}
