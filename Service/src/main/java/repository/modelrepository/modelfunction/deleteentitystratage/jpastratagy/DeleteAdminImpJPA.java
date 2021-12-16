package repository.modelrepository.modelfunction.deleteentitystratage.jpastratagy;

import helperutils.closebaseconnection.JpaUtils;
import repository.modelrepository.modelfunction.functionjpaerepositiry.AdminFunctionJpa;
import users.Administrator;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DeleteAdminImpJPA implements DeleteStratageJPA {
    @Override
    public void doDeleteEntity(int id, EntityManager em) {
        Administrator administrator = AdminFunctionJpa.doGetAdministratorById(id);
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
