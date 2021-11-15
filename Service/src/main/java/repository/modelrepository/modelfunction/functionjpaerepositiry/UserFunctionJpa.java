package repository.modelrepository.modelfunction.functionjpaerepositiry;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import users.UserImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.HashMap;

public class UserFunctionJpa {
    public static Configuration cnf = new Configuration().configure();
    public static SessionFactory sessionFactory = cnf.buildSessionFactory();
    public static EntityManager em = sessionFactory.createEntityManager();

    public static UserImpl getUserById (Integer id) {
       return getAllUser().get(id);
    }

    public static HashMap<Integer, UserImpl> getAllUser() {
            HashMap <Integer, UserImpl> result = new HashMap<>();
            result.putAll(TrainerFunctionJpa.getallTrainer());
            result.putAll(StudentFunctionJpa.getAllStudent());
            result.putAll(AdminFunctionJpa.getAllAdmin());
            return result;
    }
}
