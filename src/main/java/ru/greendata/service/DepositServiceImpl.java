package ru.greendata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.greendata.model.Deposit;
import ru.greendata.repository.DepositRepository;

import java.util.List;

import static ru.greendata.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DepositServiceImpl implements DepositService {

    private final DepositRepository repository;

    @Autowired
    public DepositServiceImpl(DepositRepository repository) {
        this.repository = repository;
    }


    @Override
    public Deposit create(Deposit deposit, int clientId, int bankId) {
        Assert.notNull(deposit, "deposit must not be null");
        return repository.save(deposit, clientId, bankId);
    }

    @Override
    public void delete(int id, int clientId, int bankId) {
        checkNotFoundWithId(repository.delete(id, clientId, bankId), id);
    }

    @Override
    public Deposit get(int id, int clientId, int bankId) {
        return checkNotFoundWithId(repository.get(id, clientId, bankId), id);
    }

    @Override
    public void update(Deposit deposit, int clientId, int bankId) {
        Assert.notNull(deposit, "deposit must not be null");
        checkNotFoundWithId(repository.save(deposit, clientId, bankId), deposit.getId());
    }

    @Override
    public List<Deposit> getAll() {
        return repository.getAll();
    }

    @Override
    public List<Deposit> getByBank(int bankId) {
        return repository.getByBank(bankId);
    }

    @Override
    public List<Deposit> getByClient(int clientId) {
        return repository.getByClient(clientId);
    }
}
