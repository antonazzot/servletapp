package repository.hibernaterepositiry;

import repository.modelrepository.UserRepository;
import repository.modelrepository.UserRepositoryImplPostgres;
import users.UserImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class UserRepositoryImplHibernate implements UserRepository {
    private static UserRepositoryImplHibernate instance;
    public static Configuration cnf = new Configuration();
    public static SessionFactory sessionFactory = cnf.buildSessionFactory();

    private UserRepositoryImplHibernate () {
    }

    public static UserRepositoryImplHibernate getInstance() {

        if (instance == null) {
            synchronized (UserRepositoryImplPostgres.class) {
                if (instance == null) {
                    instance = new UserRepositoryImplHibernate();
                }
            }
        }
        return instance;
    }

    @Override
    public HashMap<Integer, UserImpl> allUser() {
        return null;
    }

    @Override
    public HashMap<Integer, UserImpl> allTrainer() {
        return null;
    }

    @Override
    public HashMap<Integer, UserImpl> allStudent() {
        return null;
    }

    @Override
    public HashMap<Integer, UserImpl> allAdmin() {
        return null;
    }

    @Override
    public UserImpl getUserById(Integer id) {
        return null;
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
