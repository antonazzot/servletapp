package repository.modelrepository.modelfunction.deleteentitystratage;

import helperutils.closebaseconnection.JpaUtils;
import repository.modelrepository.modelfunction.functionjpaerepositiry.StudentFunctionJpa;
import users.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DeleteStudentImp implements DeleteStratage {
    @Override
    public void doDeleteEntity(int id, EntityManager em) {
        Student student = StudentFunctionJpa.doGetStudentById(id);
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.remove(student);
            transaction.commit();
        }
        catch (Exception e) {
        JpaUtils.rollBackQuietly(em, e);
    } finally {
        JpaUtils.closeQuietly(em);
    }
    }
}
