package repository.modelrepository.modelfunction.deleteentitystratage;

import helperutils.closebaseconnection.JpaUtils;
import repository.modelrepository.modelfunction.functionjpaerepositiry.StudentFunctionJpa;
import repository.threadmodelrep.threadfunction.functionjpa.TheamFunctionJpa;
import threadmodel.Theams;
import users.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DeleteTheamImp implements DeleteStratage {
    @Override
    public void doDeleteEntity(int id, EntityManager em) {
        Theams theams = TheamFunctionJpa.gettheamById(id);
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(theams);
            transaction.commit();
        }
        catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
    }
}
