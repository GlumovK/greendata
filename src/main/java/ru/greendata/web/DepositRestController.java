package ru.greendata.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.greendata.model.Deposit;
import ru.greendata.service.DepositService;

import java.util.List;

import static ru.greendata.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(DepositRestController.REST_URL)
public class DepositRestController {

    static final String REST_URL = "/rest/deposits";

    @Autowired
    private DepositService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Deposit> getAll() {
        return service.getAll();
    }

    @GetMapping(value = "/{id}/{clientId}/{bankId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Deposit get(@PathVariable("id") int id, @PathVariable("clientId") int clientId, @PathVariable("bankId") int bankId) {
        return service.get(id, clientId, bankId);
    }

    @DeleteMapping(value = "/{id}/{clientId}/{bankId}")
    public void delete(@PathVariable("id") int id, @PathVariable("clientId") int clientId, @PathVariable("bankId") int bankId) {
        service.delete(id, clientId, bankId);
    }

    @PutMapping(value = "/{id}/{clientId}/{bankId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Deposit deposit, @PathVariable("id") int id, @PathVariable("clientId") int clientId, @PathVariable("bankId") int bankId) {
        assureIdConsistent(deposit, id);
        service.update(deposit, clientId, bankId);
    }

    @GetMapping(value = "/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Deposit> getByName(@RequestParam("bankId") int bankId) {
        return service.getByBank(bankId);
    }
}
