package ru.greendata.repository;

import ru.greendata.model.Client;

import java.util.List;

public interface ClientRepository {
    Client save(Client client);

    boolean delete(int id);

    Client get(int id);

    Client getByName(String name);

    List<Client> getAll();
}
