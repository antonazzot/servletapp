package repository.threadmodelrep.threadservices.updategroupstratagy.jpaupdatestratage;

import helperutils.myexceptionutils.MyJpaException;
import helperutils.closebaseconnection.JpaUtils;
import lombok.extern.slf4j.Slf4j;
import repository.threadmodelrep.threadservices.updategroupstratagy.UpdateStratageJpa;
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

