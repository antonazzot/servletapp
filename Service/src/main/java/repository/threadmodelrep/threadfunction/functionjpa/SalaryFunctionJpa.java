package repository.threadmodelrep.threadfunction.functionjpa;

import helperutils.closebaseconnection.JpaUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import repository.modelrepository.UserRepositoryImplJpa;
import repository.modelrepository.modelfunction.functionjpaerepositiry.TrainerFunctionJpa;
import threadmodel.Salary;
import users.Trainer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SalaryFunctionJpa {
    public static Configuration cnf = new Configuration().configure();
    public static SessionFactory sessionFactory = cnf.buildSessionFactory();

    public static Map<Trainer, List<Salary>> gettrainerSalary() {
        Map<Trainer, List<Salary>> result = new HashMap<>();
        UserRepositoryImplJpa.getInstance().allTrainer().values()
                .stream().map(trainer -> (Trainer)trainer)
                .forEach(trainer -> result.put(trainer, trainer.getSalarylist()));
        return result;
    }

    public static void doaddSalaryToTrainer(int trainerId, int salaryValue ) {
       Trainer trainer =  TrainerFunctionJpa.doGetTrainerById(trainerId);
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
