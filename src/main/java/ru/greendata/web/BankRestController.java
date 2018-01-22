package ru.greendata.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.greendata.model.Bank;
import ru.greendata.service.BankService;

import java.net.URI;
import java.util.List;

import static ru.greendata.util.ValidationUtil.assureIdConsistent;
import static ru.greendata.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(BankRestController.REST_URL)
public class BankRestController {

    static final String REST_URL = "/rest/banks";

    @Autowired
    private BankService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Bank> getAll() {
        return service.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Bank get(@PathVariable("id") int id) {
        return service.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Bank> createWithLocation(@RequestBody Bank bank) {
        checkNew(bank);
        Bank created = service.create(bank);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id) {
        service.delete(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Bank bank, @PathVariable("id") int id) {
        assureIdConsistent(bank, id);
        service.update(bank);
    }

    @GetMapping(value = "/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public Bank getByBic(@RequestParam("bic") String bic) {
        return service.getByBic(bic);
    }
}
