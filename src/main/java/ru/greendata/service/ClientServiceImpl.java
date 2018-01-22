package ru.greendata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.greendata.model.Client;
import ru.greendata.repository.ClientRepository;

import java.util.List;

import static ru.greendata.util.ValidationUtil.checkNotFound;
import static ru.greendata.util.ValidationUtil.checkNotFoundWithId;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    @Autowired
    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Client create(Client client) {
        Assert.notNull(client, "client must not be null");
        return repository.save(client);
    }

    @Override
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Client get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public Client getByName(String name) {
        Assert.notNull(name, "email must not be null");
        return checkNotFound(repository.getByName(name), "name=" + name);
    }

    @Override
    public void update(Client client) {
        Assert.notNull(client, "client must not be null");
        checkNotFoundWithId(repository.save(client), client.getId());
    }

    @Override
    public List<Client> getAll() {
        return repository.getAll();
    }
}
