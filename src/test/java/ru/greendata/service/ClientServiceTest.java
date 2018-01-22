package ru.greendata.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.greendata.model.Client;
import ru.greendata.util.exception.NotFoundException;

import java.util.List;

import static ru.greendata.ClientTestData.*;
import static ru.greendata.model.LegalForm.FORM_PERSON;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class ClientServiceTest {

    @Autowired
    private ClientService service;

    @Test
    public void create() throws Exception {
        Client newClient = new Client(null, "Obama B.", "Obama", "USA, NY", FORM_PERSON);
        Client created = service.create(newClient);
        newClient.setId(created.getId());
        assertMatch(service.getAll(), newClient, PUTIN, ROGA, TRUMP);
    }

    @Test
    public void delete() throws Exception {
        service.delete(PUTIN_ID);
        assertMatch(service.getAll(), ROGA, TRUMP);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void get() throws Exception {
        Client client = service.get(PUTIN_ID);
        assertMatch(client, PUTIN);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void getByName() throws Exception {
        Client client = service.getByName("Putin V.V.");
        assertMatch(client, PUTIN);
    }

    @Test
    public void update() throws Exception {
        Client updated = new Client(PUTIN);
        updated.setName("UpdatedName");
        service.update(updated);
        assertMatch(service.get(PUTIN_ID), updated);
    }

    @Test
    public void getAll() throws Exception {
        List<Client> all = service.getAll();
        assertMatch(all, PUTIN, ROGA, TRUMP);
    }
}
