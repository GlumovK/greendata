package ru.greendata.service;

import ru.greendata.model.Client;
import ru.greendata.util.exception.NotFoundException;

import java.util.List;

public interface ClientService {

    Client create(Client client);

    void delete(int id) throws NotFoundException;

    Client get(int id) throws NotFoundException;

    Client getByName(String name) throws NotFoundException;

    void update(Client client) throws NotFoundException;

    List<Client> getAll();
}
