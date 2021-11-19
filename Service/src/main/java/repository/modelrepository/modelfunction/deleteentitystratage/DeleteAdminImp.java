package repository.modelrepository.modelfunction.deleteentitystratage;

import helperutils.closebaseconnection.JpaUtils;
import repository.modelrepository.modelfunction.functionjpaerepositiry.AdminFunctionJpa;
import repository.modelrepository.modelfunction.functionjpaerepositiry.StudentFunctionJpa;
import users.Administrator;
import users.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DeleteAdminImp implements DeleteStratage {
    @Override
    public void doDeleteEntity(int id, EntityManager em) {
        Administrator administrator = AdminFunctionJpa.doGetAdministratorById(id);
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(administrator);
            transaction.commit();
        }
        catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
    }
}
