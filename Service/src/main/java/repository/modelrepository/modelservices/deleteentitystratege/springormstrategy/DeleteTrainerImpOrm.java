package repository.modelrepository.modelservices.deleteentitystratege.springormstrategy;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import repository.RepositoryFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
@Component
public class DeleteTrainerImpOrm implements DeleteStratageOrm {
    @Autowired
    @PersistenceContext
    private final EntityManager em;

    @Override
    public void doDeleteEntity(int id) {
        em.remove(RepositoryFactory.getRepository().getTrainerById(id));
    }
}
