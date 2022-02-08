package repository.modelrepository.modelservices.deleteentitystratege.jpastrategy;

import helperutils.closebaseconnection.JpaUtils;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DeleteGroupImpJPA implements DeleteStratageJPA {
    @Override
    public void doDeleteEntity(int id, EntityManager em) {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            Group group = em.find(Group.class, id);
            em.remove(group);
            transaction.commit();
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
    }
}
