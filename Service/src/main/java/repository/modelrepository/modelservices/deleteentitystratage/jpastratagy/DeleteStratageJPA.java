package repository.modelrepository.modelservices.deleteentitystratage.jpastratagy;

import javax.persistence.EntityManager;

public interface DeleteStratageJPA {
    void doDeleteEntity(int id, EntityManager em);
}
