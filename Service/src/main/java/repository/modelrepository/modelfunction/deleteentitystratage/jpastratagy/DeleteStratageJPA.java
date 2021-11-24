package repository.modelrepository.modelfunction.deleteentitystratage.jpastratagy;

import javax.persistence.EntityManager;

public interface DeleteStratageJPA {
    void doDeleteEntity(int id, EntityManager em);
}
