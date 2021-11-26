package repository.modelrepository;

import lombok.extern.slf4j.Slf4j;
import repository.modelrepository.modelfunction.functionpostgress.AdminFunctionPostgres;
import repository.modelrepository.modelfunction.functionpostgress.StudentFunctionPostgres;
import repository.modelrepository.modelfunction.functionpostgress.TrainerFunctionPostgres;
import repository.modelrepository.modelfunction.functionpostgress.UsersFunctionPostgres;
import users.Student;
import users.UserImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class UserRepositoryImplPostgres implements UserRepository {

    private static volatile UserRepositoryImplPostgres instance;

    private UserRepositoryImplPostgres() {
    }

    public static UserRepositoryImplPostgres getInstance() {
        if (instance == null) {
            synchronized (UserRepositoryImplPostgres.class) {
                if (instance == null) {
                    instance = new UserRepositoryImplPostgres();
                }
            }
        }
        return instance;
    }

    @Override
    public Map<Integer, UserImpl> allUser() {
        return UsersFunctionPostgres.allUser();
    }

    @Override
    public Map<Integer, UserImpl> allTrainer() {
        return TrainerFunctionPostgres.getallTrainer();
    }

    @Override
    public Map<Integer, UserImpl> allStudent() {
        return StudentFunctionPostgres.allStudent();
    }

    @Override
    public Map<Integer, UserImpl> allAdmin() {
        return AdminFunctionPostgres.allAdmin();
    }

    @Override
    public UserImpl getUserById(Integer id) {
        return UsersFunctionPostgres.getUserById(id);
    }

    @Override
    public int saveUser(UserImpl user) {
        return UsersFunctionPostgres.saveUser(user);
    }

    @Override
    public Optional<UserImpl> removeUser(Integer id, String entity) {
        return UsersFunctionPostgres.removeUser(id, entity);
    }

    @Override
    public UserImpl updateUser(UserImpl user) {
        return UsersFunctionPostgres.updateUser(user);
    }

    @Override
    public Map<Integer, UserImpl> freeTrainer() {
        return TrainerFunctionPostgres.freeTrainer();
    }

    @Override
    public List<Student> studentFromGroup(Integer groupId) {
        return StudentFunctionPostgres.studentFromGroup(groupId);
    }

}
