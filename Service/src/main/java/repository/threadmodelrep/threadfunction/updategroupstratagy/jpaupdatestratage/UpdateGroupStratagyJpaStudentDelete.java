package repository.threadmodelrep.threadfunction.updategroupstratagy.jpaupdatestratage;

import helperutils.MyExceptionUtils.MyJpaException;
import helperutils.closebaseconnection.JpaUtils;
import lombok.extern.slf4j.Slf4j;
import repository.threadmodelrep.threadfunction.updategroupstratagy.UpdateStratageJpa;
import threadmodel.Group;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Slf4j
public class UpdateGroupStratagyJpaStudentDelete implements UpdateStratageJpa {

    @Override
    public void updateGroup(Group group, int[] entytiIdforact, EntityManager em) throws MyJpaException {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            for (int i : entytiIdforact) {
                group.getStudentMap().remove(i);
            }
            em.merge(group);
            transaction.commit();
        }
        catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
    }
    }

