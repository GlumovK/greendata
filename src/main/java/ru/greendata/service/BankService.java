package ru.greendata.service;

import ru.greendata.model.Bank;
import ru.greendata.util.exception.NotFoundException;


import java.util.List;

public interface BankService {

    Bank create(Bank bank);

    void delete(int id) throws NotFoundException;

    Bank get(int id) throws NotFoundException;

    Bank getByBic(String bic) throws NotFoundException;

    void update(Bank bank) throws NotFoundException;

    List<Bank> getAll();
}
