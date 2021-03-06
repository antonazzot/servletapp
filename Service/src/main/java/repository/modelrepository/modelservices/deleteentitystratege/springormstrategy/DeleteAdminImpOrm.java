package repository.modelrepository.modelservices.deleteentitystratege.springormstrategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import repository.RepositoryFactory;
import users.Administrator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional(propagation = Propagation.REQUIRED)
@Component
@Slf4j
@RequiredArgsConstructor
public class DeleteAdminImpOrm implements DeleteStratageOrm {
    @Autowired
    @PersistenceContext
    private final EntityManager em;

    @Override
    public void doDeleteEntity(int id) {
        Administrator administratorById = RepositoryFactory.getRepository().getAdministratorById(id);
        em.remove(administratorById);
    }
}
