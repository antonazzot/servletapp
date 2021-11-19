package repository.modelrepository.modelfunction.deleteentitystratage;

import javax.persistence.EntityManager;

public interface DeleteStratage {
    public  void doDeleteEntity (int id, EntityManager em);
}
