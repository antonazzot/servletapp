package repository.modelrepository;

import repository.modelrepository.modelfunction.functionjpaerepositiry.AdminFunctionJpa;
import repository.modelrepository.modelfunction.functionjpaerepositiry.StudentFunctionJpa;
import repository.modelrepository.modelfunction.functionjpaerepositiry.TrainerFunctionJpa;
import repository.modelrepository.modelfunction.functionjpaerepositiry.UserFunctionJpa;
import users.Trainer;
import users.UserImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class UserRepositoryImplJpa implements UserRepository {
    private static volatile UserRepositoryImplJpa instance;
    public static Configuration cnf = new Configuration().configure();
    public static SessionFactory sessionFactory = cnf.buildSessionFactory();
    public static EntityManager em = sessionFactory.createEntityManager();

    private UserRepositoryImplJpa() {
    }

    public static UserRepositoryImplJpa getInstance() {

        if (instance == null) {
            synchronized (UserRepositoryImplPostgres.class) {
                if (instance == null) {
                    instance = new UserRepositoryImplJpa();
                }
            }
        }
        return instance;
    }

    @Override
    public HashMap<Integer, UserImpl> allUser() {
       return UserFunctionJpa.getAllUser();

    }

    @Override
    public HashMap<Integer, UserImpl> allTrainer() {
       return TrainerFunctionJpa.getallTrainer();
    }

    @Override
    public HashMap<Integer, UserImpl> allStudent() {
        return StudentFunctionJpa.getAllStudent();
    }

    @Override
    public HashMap<Integer, UserImpl> allAdmin() {
        return AdminFunctionJpa.getAllAdmin();
    }

    @Override
    public UserImpl getUserById(Integer id) {
        return UserFunctionJpa.getUserById(id);
    }

    @Override
    public int saveUser(UserImpl user) {
     return  0;
    }

    @Override
    public Optional<UserImpl> removeUser(Integer id, String entity) {
        return Optional.empty();
    }

    @Override
    public UserImpl updateUser(UserImpl user) {
        return null;
    }

    @Override
    public HashMap<Integer, UserImpl> freeTrainer() {
        return null;
    }

    @Override
    public ArrayList<UserImpl> studentFromGroup(Integer groupId) {
        return null;
    }
}
