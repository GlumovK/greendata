package ru.greendata.repository;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.greendata.model.Bank;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaBankRepositoryImpl implements BankRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Bank save(Bank bank) {
        if (bank.isNew()) {
            em.persist(bank);
            return bank;
        } else {
            return em.merge(bank);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery(Bank.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public Bank get(int id) {
        return em.find(Bank.class, id);
    }

    @Override
    public Bank getByBic(String bic) {
        List<Bank> banks = em.createNamedQuery(Bank.BY_BIC, Bank.class)
                .setParameter(1, bic)
                .getResultList();
        return DataAccessUtils.singleResult(banks);
    }

    @Override
    public List<Bank> getAll() {
        return em.createNamedQuery(Bank.ALL_SORTED, Bank.class).getResultList();
    }
}
