package repository.modelrepository.modelfunction.deleteentitystratage.jpastratagy;

import helperutils.closebaseconnection.JpaUtils;
import repository.threadmodelrep.threadfunction.functionjpa.TheamFunctionJpa;
import threadmodel.Theams;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DeleteTheamImpJPA implements DeleteStratageJPA {
    @Override
    public void doDeleteEntity(int id, EntityManager em) {
        Theams theams = TheamFunctionJpa.gettheamById(id);
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(theams);
            transaction.commit();
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
    }
}
