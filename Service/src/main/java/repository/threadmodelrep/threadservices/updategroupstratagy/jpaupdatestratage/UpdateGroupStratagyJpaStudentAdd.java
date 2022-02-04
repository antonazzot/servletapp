package repository.threadmodelrep.threadservices.updategroupstratagy.jpaupdatestratage;

import helperutils.closebaseconnection.JpaUtils;
import helperutils.myexceptionutils.MyJpaException;
import lombok.extern.slf4j.Slf4j;
import repository.RepositoryFactory;
import repository.threadmodelrep.threadservices.updategroupstratagy.UpdateStratageJpa;
import threadmodel.Group;
import users.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Slf4j
public class UpdateGroupStratagyJpaStudentAdd implements UpdateStratageJpa {

    @Override
    public void updateGroup(Group group, int[] entytiIdforact, EntityManager em) throws MyJpaException {
        try {
            Student student = null;
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            for (int id : entytiIdforact) {
                student = RepositoryFactory.getRepository().getStudentById(id);
                group.addStudent(student);
            }
            em.merge(group);
            transaction.commit();
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
    }
}


