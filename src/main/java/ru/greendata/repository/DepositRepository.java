package ru.greendata.repository;


import ru.greendata.model.Deposit;

import java.util.List;

public interface DepositRepository {
    Deposit save(Deposit deposit, int clientId, int bankId);

    boolean delete(int id, int clientId, int bankId);

    Deposit get(int id, int clientId, int bankId);

    List<Deposit> getByBank(int bankId);

    List<Deposit> getByClient(int clientId);

    List<Deposit> getAll();

}
