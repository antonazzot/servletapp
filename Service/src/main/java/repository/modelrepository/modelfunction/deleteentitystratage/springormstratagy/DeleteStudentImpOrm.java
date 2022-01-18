package repository.modelrepository.modelfunction.deleteentitystratage.springormstratagy;

import helperutils.closebaseconnection.JpaUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import repository.RepositoryFactory;
import users.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
@Transactional(propagation = Propagation.REQUIRED)
@Component
@Slf4j
@RequiredArgsConstructor
public class DeleteStudentImpOrm implements DeleteStratageOrm {
    @Autowired
    @PersistenceContext
    private final EntityManager em;
    @Override
    public void doDeleteEntity(int id) {
        Student studentById = RepositoryFactory.getRepository().getStudentById(id);
        log.info("student for delete ={}", studentById.getInf());
        em.remove(studentById);
    }
}
