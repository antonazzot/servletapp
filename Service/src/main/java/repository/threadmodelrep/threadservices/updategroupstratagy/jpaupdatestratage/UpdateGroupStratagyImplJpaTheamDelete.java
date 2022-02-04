package repository.threadmodelrep.threadservices.updategroupstratagy.jpaupdatestratage;

import helperutils.closebaseconnection.JpaUtils;
import helperutils.myexceptionutils.MyJpaException;
import lombok.extern.slf4j.Slf4j;
import repository.threadmodelrep.threadservices.updategroupstratagy.UpdateStratageJpa;
import threadmodel.Group;
import threadmodel.Theams;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

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
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
    }
}
