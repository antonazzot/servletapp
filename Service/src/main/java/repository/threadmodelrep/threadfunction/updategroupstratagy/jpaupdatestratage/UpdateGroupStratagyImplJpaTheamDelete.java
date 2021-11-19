package repository.threadmodelrep.threadfunction.updategroupstratagy.jpaupdatestratage;

import helperutils.MyExceptionUtils.MyJpaException;
import helperutils.closebaseconnection.JpaUtils;
import lombok.extern.slf4j.Slf4j;
import repository.threadmodelrep.threadfunction.updategroupstratagy.UpdateStratageJpa;
import threadmodel.Group;
import threadmodel.Theams;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Set;

@Slf4j
public class UpdateGroupStratagyImplJpaTheamDelete implements UpdateStratageJpa {

    @Override
    public void updateGroup(Group group, int[] entytiIdforact, EntityManager em) throws MyJpaException {
        try {
            Theams theams = null;
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            for (int id : entytiIdforact) {
                theams = em.find(Theams.class, id);
                group.getTheamsSet().remove(theams);
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
