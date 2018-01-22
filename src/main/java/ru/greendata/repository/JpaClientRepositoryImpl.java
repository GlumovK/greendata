package ru.greendata.repository;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.greendata.model.Client;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaClientRepositoryImpl implements ClientRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Client save(Client client) {
        if (client.isNew()) {
            em.persist(client);
            return client;
        } else {
            return em.merge(client);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery(Client.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public Client get(int id) {
        return em.find(Client.class, id);
    }

    @Override
    public Client getByName(String name) {
        List<Client> clients = em.createNamedQuery(Client.BY_NAME, Client.class)
                .setParameter(1, name)
                .getResultList();
        return DataAccessUtils.singleResult(clients);
    }

    @Override
    public List<Client> getAll() {
        return em.createNamedQuery(Client.ALL_SORTED, Client.class).getResultList();
    }


}
