package repository.modelrepository.modelfunction.functionjpaerepositiry;

import helperutils.MyExceptionUtils.MySqlException;
import helperutils.closebaseconnection.PostgresSQLUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import repository.RepositoryDatasourse;
import repository.modelrepository.modelfunction.RoleIDParametrCheker;
import repository.modelrepository.modelfunction.functionpostgress.UsersFunctionPostgres;
import repository.threadmodelrep.ThreadRepositoryImpl;
import users.Role;
import users.Trainer;
import users.UserImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class TrainerFunctionJpa {

    public static Configuration cnf = new Configuration().configure();
    public static SessionFactory sessionFactory = cnf.buildSessionFactory();
    public static EntityManager em = sessionFactory.createEntityManager();

    public static HashMap<Integer, UserImpl> getallTrainer() {
        HashMap <Integer, UserImpl> result = new HashMap<>();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        TypedQuery<Trainer> alltrainer = em.createQuery("select t from Trainer t where t.role = :role", Trainer.class);
        alltrainer.setParameter("role", Role.TRAINER);
        for (Trainer trainer : alltrainer.getResultList()) {
            result.put(trainer.getId(), trainer);
        }

        return result;
    }

    public static HashMap<Integer, UserImpl> freeTrainer() {

            return null;

    }

    private static HashMap<Integer, UserImpl> freeTrainerexecute(ArrayList<UserImpl> busyTrainer) {
        HashMap<Integer, UserImpl> result = new HashMap<>(getallTrainer());
        for (UserImpl alltrainer :
                getallTrainer().values()) {
            for (UserImpl busyTr : busyTrainer) {
                if (alltrainer.getId() == busyTr.getId())
                    result.remove(busyTr.getId());
            }
        }
        return result;
    }
}
