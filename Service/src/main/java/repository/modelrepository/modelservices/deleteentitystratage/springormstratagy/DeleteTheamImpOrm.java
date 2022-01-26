package repository.modelrepository.modelservices.deleteentitystratage.springormstratagy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Theams;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
@Component
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
@Slf4j
public class DeleteTheamImpOrm implements DeleteStratageOrm {
    @Autowired
    @PersistenceContext
    private final EntityManager em;
    @Override
    public void doDeleteEntity(int id) {
        Theams theams = ThreadRepositoryFactory.getRepository().theamById(id);
        log.info("!!!!!!!!!!!!!!!!em ={}", em.toString());
        log.info("!!!!!!!!!!!!!theam={}", theams.getTheamName());
        em.remove(theams);
    }
}
