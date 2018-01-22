package ru.greendata.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.greendata.model.Bank;
import ru.greendata.model.Client;
import ru.greendata.model.Deposit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaDepositRepositoryImpl implements DepositRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    @Transactional
    public Deposit save(Deposit deposit, int clientId, int bankId) {
        if (!deposit.isNew() && get(deposit.getId(), clientId, bankId) == null) {
            return null;
        }
        deposit.setClient(em.getReference(Client.class, clientId));
        deposit.setBank(em.getReference(Bank.class, bankId));
        if (deposit.isNew()) {
            em.persist(deposit);
            return deposit;
        } else {
            return em.merge(deposit);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int clientId, int bankId) {
        return em.createNamedQuery(Deposit.DELETE)
                .setParameter("id", id)
                .setParameter("clientId", clientId)
                .setParameter("bankId", bankId)
                .executeUpdate() != 0;
    }

    @Override
    public Deposit get(int id, int clientId, int bankId) {
        Deposit deposit = em.find(Deposit.class, id);
        return deposit != null && (deposit.getClient().getId() == clientId && deposit.getBank().getId() == bankId) ? deposit : null;
    }

    @Override
    public List<Deposit> getByBank(int bankId) {
        return em.createNamedQuery(Deposit.BY_BANK, Deposit.class)
                .setParameter("bankId", bankId)
                .getResultList();
    }

    @Override
    public List<Deposit> getByClient(int clientId) {
        return em.createNamedQuery(Deposit.BY_CLIENT, Deposit.class)
                .setParameter("clientId", clientId)
                .getResultList();
    }

    @Override
    public List<Deposit> getAll() {
        return em.createNamedQuery(Deposit.ALL_SORTED, Deposit.class).getResultList();
    }
}
