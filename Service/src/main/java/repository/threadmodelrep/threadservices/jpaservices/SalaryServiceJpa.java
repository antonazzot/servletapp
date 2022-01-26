package repository.threadmodelrep.threadservices.jpaservices;

import helperutils.closebaseconnection.JpaUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.RepositoryFactory;
import threadmodel.Salary;
import users.Trainer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@RequiredArgsConstructor
@Slf4j
public class SalaryServiceJpa {
    @Autowired
    private final SessionFactory sessionFactory;

    public  Map<Trainer, List<Salary>> gettrainerSalary() {
        Map<Trainer, List<Salary>> result = new HashMap<>();
        RepositoryFactory.getRepository().allTrainer().values()
                .stream().map(trainer -> (Trainer)trainer)
                .forEach(trainer -> result.put(trainer, trainer.getSalarylist()));
        return result;
    }

    public  void doaddSalaryToTrainer(int trainerId, int salaryValue ) {
       Trainer trainer =  RepositoryFactory.getRepository().getTrainerById(trainerId);
       Salary salary = new Salary()
               .withValue(salaryValue)
               .withTrainer(trainer);
        assert trainer != null;
        trainer.addSalary(salary);
        EntityManager em = null;
        try{
            em = sessionFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.persist(salary);
            transaction.commit();
        }
        catch (Exception e) {
            JpaUtils.rollBackQuietly(em, e);
        } finally {
            JpaUtils.closeQuietly(em);
        }
//        EntityManager em1 = sessionFactory.createEntityManager();
//        EntityTransaction transaction2 = em.getTransaction();
//        transaction2.begin();
//        trainer.addSalary(salary);
//        em.merge(trainer);
//        transaction2.commit();
//        em1.close();
    }
}
