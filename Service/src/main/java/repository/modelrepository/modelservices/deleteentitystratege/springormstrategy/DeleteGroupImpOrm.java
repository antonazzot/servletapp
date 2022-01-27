package repository.modelrepository.modelservices.deleteentitystratege.springormstrategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
@Transactional(propagation = Propagation.REQUIRED)
@Component
@RequiredArgsConstructor
@Slf4j
public class DeleteGroupImpOrm implements DeleteStratageOrm {
    @Autowired
    @PersistenceContext
    private final EntityManager em;
    @Override
    public void doDeleteEntity(int id) {
        Group group = ThreadRepositoryFactory.getRepository().allGroup().get(id);
        log.info("group for delete ={}", group.getInf());
        em.remove(group);
    }
}
