package repository.modelrepository.modelfunction.deleteentitystratage;

import helperutils.closebaseconnection.JpaUtils;
import repository.modelrepository.modelfunction.functionjpaerepositiry.StudentFunctionJpa;
import repository.modelrepository.modelfunction.functionjpaerepositiry.TrainerFunctionJpa;
import users.Student;
import users.Trainer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DeleteTrainerImp implements DeleteStratage {
    @Override
    public void doDeleteEntity(int id, EntityManager em) {
        Trainer trainer = TrainerFunctionJpa.doGetTrainerById(id);
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(trainer);
            transaction.commit();
        }
        catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
    }
}
