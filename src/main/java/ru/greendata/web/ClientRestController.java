package ru.greendata.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.greendata.model.Client;
import ru.greendata.service.ClientService;

import java.net.URI;
import java.util.List;

import static ru.greendata.util.ValidationUtil.assureIdConsistent;
import static ru.greendata.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(ClientRestController.REST_URL)
public class ClientRestController {

    static final String REST_URL = "/rest/clients";

    @Autowired
    private ClientService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Client> getAll() {
        return service.getAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Client get(@PathVariable("id") int id) {
        return service.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> createWithLocation(@RequestBody Client client) {
        checkNew(client);
        Client created = service.create(client);

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
    public void update(@RequestBody Client client, @PathVariable("id") int id) {
        assureIdConsistent(client, id);
        service.update(client);
    }

    @GetMapping(value = "/by", produces = MediaType.APPLICATION_JSON_VALUE)
    public Client getByName(@RequestParam("name") String name) {
        return service.getByName(name);
    }
}
