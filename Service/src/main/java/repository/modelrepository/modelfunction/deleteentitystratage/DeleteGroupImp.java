package repository.modelrepository.modelfunction.deleteentitystratage;

import helperutils.closebaseconnection.JpaUtils;
import repository.modelrepository.modelfunction.functionjpaerepositiry.StudentFunctionJpa;
import repository.threadmodelrep.threadfunction.functionjpa.GroupFunctionJpa;
import threadmodel.Group;
import users.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class DeleteGroupImp implements DeleteStratage {
    @Override
    public void doDeleteEntity(int id, EntityManager em) {
            Group group = GroupFunctionJpa.getGroupById(id);
            try {
                EntityTransaction transaction = em.getTransaction();
                transaction.begin();
                em.remove(group);
                transaction.commit();
            }
            catch (Exception e) {
                JpaUtils.rollBackQuietly(em, e);
            } finally {
                JpaUtils.closeQuietly(em);

        }
    }
}
