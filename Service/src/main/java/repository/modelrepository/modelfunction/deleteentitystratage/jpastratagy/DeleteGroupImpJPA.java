package repository.modelrepository.modelfunction.deleteentitystratage.jpastratagy;

import helperutils.closebaseconnection.JpaUtils;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DeleteGroupImpJPA implements DeleteStratageJPA {
    @Override
    public void doDeleteEntity(int id, EntityManager em) {
        Group group = ThreadRepositoryFactory.getRepository().allGroup().get(id);
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(group);
            transaction.commit();
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
    }
}
