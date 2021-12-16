package repository.threadmodelrep.threadfunction.updategroupstratagy.jpaupdatestratage;

import helperutils.myexceptionutils.MyJpaException;
import helperutils.closebaseconnection.JpaUtils;
import lombok.extern.slf4j.Slf4j;
import repository.threadmodelrep.threadfunction.functionjpa.TheamFunctionJpa;
import repository.threadmodelrep.threadfunction.updategroupstratagy.UpdateStratageJpa;
import threadmodel.Group;
import threadmodel.Theams;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Slf4j
public class UpdateGroupStratagyImplJpaTheamAdd implements UpdateStratageJpa {
    @Override
    public void updateGroup(Group group, int[] entytiIdforact, EntityManager em) throws MyJpaException {
        try {
            Theams theams = null;
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            log.info("UpdateAddTheam = {}", group.getTheamsSet().toString());
            for (int id : entytiIdforact) {
                theams = TheamFunctionJpa.gettheamById(id);
                group.addTheam(theams);
            }
            log.info("UpdateAddTheam after add = {}", group.getTheamsSet().toString());
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
