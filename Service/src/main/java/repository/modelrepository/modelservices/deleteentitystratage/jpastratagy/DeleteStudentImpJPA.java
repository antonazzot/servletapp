package repository.modelrepository.modelservices.deleteentitystratage.jpastratagy;

import helperutils.closebaseconnection.JpaUtils;
import repository.RepositoryFactory;
import users.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DeleteStudentImpJPA implements DeleteStratageJPA {
    @Override
    public void doDeleteEntity(int id, EntityManager em) {
        Student student = RepositoryFactory.getRepository().getStudentById(id);
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(student);
            transaction.commit();
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
    }
}
