package repository.threadmodelrep.threadfunction.functionjpa;

import helperutils.MyExceptionUtils.MySqlException;
import helperutils.closebaseconnection.PostgresSQLUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import repository.RepositoryDatasourse;
import repository.RepositoryFactory;
import repository.modelrepository.UserRepositoryImplJpa;
import repository.modelrepository.modelfunction.functionjpaerepositiry.TrainerFunctionJpa;
import repository.threadmodelrep.ThreadRepositoryImplJpa;
import threadmodel.Salary;
import users.Trainer;
import users.UserImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class SalaryFunctionJpa {
    private static final RepositoryDatasourse datasourse = RepositoryDatasourse.getInstance();
    public static Configuration cnf = new Configuration().configure();
    public static SessionFactory sessionFactory = cnf.buildSessionFactory();

    public static HashMap<Trainer, List<Salary>> gettrainerSalary() {
        HashMap<Trainer, List<Salary>> result = new HashMap<>();
        UserRepositoryImplJpa.getInstance().allTrainer().values()
                .stream().map(trainer -> (Trainer)trainer)
                .forEach(trainer -> result.put(trainer, trainer.getSalarylist()));
        return result;
    }

    public static void doaddSalaryToTrainer(int trainerId, int salaryValue ) {
       Trainer trainer =  TrainerFunctionJpa.doGetTrainerById(trainerId);
       Salary salary = new Salary()
               .withValue(salaryValue);
       trainer.addSalary(salary);
        EntityManager em = sessionFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
//        em.persist(salary);
        em.merge(trainer);
        transaction.commit();
        em.close();
    }
}
