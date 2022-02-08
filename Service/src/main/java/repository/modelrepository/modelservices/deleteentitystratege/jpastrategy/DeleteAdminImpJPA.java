package repository.modelrepository.modelservices.deleteentitystratege.jpastrategy;

import helperutils.closebaseconnection.JpaUtils;
import repository.RepositoryFactory;
import users.Administrator;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DeleteAdminImpJPA implements DeleteStratageJPA {
    @Override
    public void doDeleteEntity(int id, EntityManager em) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            Administrator administrator = em.find(Administrator.class, id);
            em.remove(administrator);
            transaction.commit();
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
    }
}
