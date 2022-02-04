package repository.threadmodelrep.threadservices.updategroupstratagy.jpaupdatestratage;

import helperutils.closebaseconnection.JpaUtils;
import helperutils.myexceptionutils.MyJpaException;
import lombok.extern.slf4j.Slf4j;
import repository.RepositoryFactory;
import repository.threadmodelrep.threadservices.updategroupstratagy.UpdateStratageJpa;
import threadmodel.Group;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Slf4j
public class UpdateGroupStratagyImplJpaTrainerChange implements UpdateStratageJpa {

    @Override
    public void updateGroup(Group group, int[] entytiIdforact, EntityManager em) throws MyJpaException {
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            group.withTrainer(RepositoryFactory.getRepository().getTrainerById(entytiIdforact[0]));
            em.merge(group);
            transaction.commit();
        } catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
    }
}

