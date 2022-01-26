package repository.modelrepository.modelservices.deleteentitystratage.jpastratagy;

import helperutils.closebaseconnection.JpaUtils;
import repository.RepositoryFactory;
import users.Administrator;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DeleteAdminImpJPA implements DeleteStratageJPA {
    @Override
    public void doDeleteEntity(int id, EntityManager em) {
        Administrator administrator = RepositoryFactory.getRepository().getAdministratorById(id);
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(administrator);
            transaction.commit();
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
    }
}
