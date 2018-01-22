package ru.greendata.service;


import ru.greendata.model.Deposit;
import ru.greendata.util.exception.NotFoundException;

import java.util.List;

public interface DepositService {

    Deposit create(Deposit deposit, int clientId, int bankId);

    void delete(int id, int clientId, int bankId) throws NotFoundException;

    Deposit get(int id, int clientId, int bankId) throws NotFoundException;

    void update(Deposit deposit, int clientId, int bankId) throws NotFoundException;

    List<Deposit> getAll();

    List<Deposit> getByBank(int bankId) throws NotFoundException;

    List<Deposit> getByClient(int clientId) throws NotFoundException;


}
