package repository.modelrepository.modelservices.deleteentitystratage.jpastratagy;

import helperutils.closebaseconnection.JpaUtils;
import repository.RepositoryFactory;
import users.Trainer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DeleteTrainerImpJPA implements DeleteStratageJPA {
    @Override
    public void doDeleteEntity(int id, EntityManager em) {
        Trainer trainer = RepositoryFactory.getRepository().getTrainerById(id);
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(trainer);
            transaction.commit();
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
    }
}
