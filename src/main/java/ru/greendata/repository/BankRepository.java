package ru.greendata.repository;

import ru.greendata.model.Bank;
import java.util.List;

public interface BankRepository {
    Bank save(Bank bank);

    boolean delete(int id);

    Bank get(int id);

    Bank getByBic(String bic);

    List<Bank> getAll();
}
