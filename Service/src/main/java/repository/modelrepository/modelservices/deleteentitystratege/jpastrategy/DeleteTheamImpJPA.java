package repository.modelrepository.modelservices.deleteentitystratege.jpastrategy;

import helperutils.closebaseconnection.JpaUtils;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Theams;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DeleteTheamImpJPA implements DeleteStratageJPA {
    @Override
    public void doDeleteEntity(int id, EntityManager em) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            Theams theams = em.find(Theams.class, id);
            em.remove(theams);
            transaction.commit();
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
    }
}
