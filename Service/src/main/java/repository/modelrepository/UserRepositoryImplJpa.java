package repository.modelrepository;

import repository.modelrepository.modelfunction.functionjpaerepositiry.AdminFunctionJpa;
import repository.modelrepository.modelfunction.functionjpaerepositiry.StudentFunctionJpa;
import repository.modelrepository.modelfunction.functionjpaerepositiry.TrainerFunctionJpa;
import repository.modelrepository.modelfunction.functionjpaerepositiry.UserFunctionJpa;
import users.Administrator;
import users.Student;
import users.Trainer;
import users.UserImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class UserRepositoryImplJpa implements UserRepository {
    private static volatile UserRepositoryImplJpa instance;

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
     return UserFunctionJpa.doSaveUser (user);
    }

    @Override
    public Optional<UserImpl> removeUser(Integer id, String entity) {
        return UserFunctionJpa.doRemoveUser(id, entity);
    }

    @Override
    public UserImpl updateUser(UserImpl user) {
        return UserFunctionJpa.doUpdateUser(user);
    }

    @Override
    public HashMap<Integer, UserImpl> freeTrainer() {
      return TrainerFunctionJpa.freeTrainer();
    }

    @Override
    public ArrayList<Student> studentFromGroup(Integer groupId) {
        return StudentFunctionJpa.getStudentFromGroup(groupId);
    }

    public Trainer getTrainerById (int id) {
     return TrainerFunctionJpa.doGetTrainerById (id);
    }

    public Administrator getAdministratorById (int id) {
        return AdminFunctionJpa.doGetAdministratorById (id);
    }

    public Student getStudentById (int id) {
        return StudentFunctionJpa.doGetStudentById (id);
    }
}
