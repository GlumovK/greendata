package ru.greendata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.greendata.model.Bank;
import ru.greendata.repository.BankRepository;

import java.util.List;

import static ru.greendata.util.ValidationUtil.checkNotFound;
import static ru.greendata.util.ValidationUtil.checkNotFoundWithId;

@Service
public class BankServiceImpl implements BankService {

    private final BankRepository repository;

    @Autowired
    public BankServiceImpl(BankRepository repository) {
        this.repository = repository;
    }

    @Override
    public Bank create(Bank bank) {
        Assert.notNull(bank, "bank must not be null");
        return repository.save(bank);
    }

    @Override
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Bank get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public Bank getByBic(String bic) {
        Assert.notNull(bic, "bic must not be null");
        return checkNotFound(repository.getByBic(bic), "bic=" + bic);
    }

    @Override
    public void update(Bank bank) {
        Assert.notNull(bank, "bank must not be null");
        checkNotFoundWithId(repository.save(bank), bank.getId());
    }

    @Override
    public List<Bank> getAll() {
        return repository.getAll();
    }
}
