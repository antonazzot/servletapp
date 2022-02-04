package repository.modelrepository.modelservices.deleteentitystratege.jpastrategy;

import javax.persistence.EntityManager;

public interface DeleteStratageJPA {
    void doDeleteEntity(int id, EntityManager em);
}
