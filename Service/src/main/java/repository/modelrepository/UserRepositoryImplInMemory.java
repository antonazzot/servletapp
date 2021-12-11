package repository.modelrepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import repository.modelrepository.modelfunction.functionunmemory.AdminFunctionMemory;
import repository.modelrepository.modelfunction.functionunmemory.StudentFunctionMemory;
import repository.modelrepository.modelfunction.functionunmemory.TrainerFunctionMemory;
import repository.modelrepository.modelfunction.functionunmemory.UsersFunctionMemory;
import users.Administrator;
import users.Student;
import users.Trainer;
import users.UserImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@Repository("Memory")
@Slf4j
public class UserRepositoryImplInMemory implements UserRepository {
//    private static volatile UserRepositoryImplInMemory instance;
//
//    private UserRepositoryImplInMemory() {
//    }
//
//    public static UserRepositoryImplInMemory getInstance() {
//        if (instance == null) {
//            synchronized (UserRepositoryImplInMemory.class) {
//                if (instance == null) {
//                    instance = new UserRepositoryImplInMemory();
//                }
//            }
//        }
//        return instance;
//    }

    @Override
    public Map<Integer, UserImpl> allUser() {
        return UsersFunctionMemory.getallUser();
    }

    @Override
    public Map<Integer, UserImpl> allTrainer() {
        return TrainerFunctionMemory.getallTrainer();
    }

    @Override
    public Map<Integer, UserImpl> allStudent() {
        return StudentFunctionMemory.getallStudent();
    }

    @Override
    public Map<Integer, UserImpl> allAdmin() {
        return AdminFunctionMemory.getallAdmin();
    }

    @Override
    public UserImpl getUserById(Integer id) {
        return UsersFunctionMemory.getUserById(id);
    }

    @Override
    public int saveUser(UserImpl user) {
        return UsersFunctionMemory.saveUser(user);
    }

    @Override
    public Optional<UserImpl> removeUser(Integer id, String entity) {
        return UsersFunctionMemory.doremoveUser(id, entity);
    }

    @Override
    public UserImpl updateUser(UserImpl user) {
        return UsersFunctionMemory.doupdateUser(user);
    }

    @Override
    public Map<Integer, UserImpl> freeTrainer() {
        return TrainerFunctionMemory.getfreeTrainer();
    }

    @Override
    public List<Student> studentFromGroup(Integer groupId) {
        return StudentFunctionMemory.studentFromGroup(groupId);
    }

    @Override
    public Trainer getTrainerById(int id) {
        return null;
    }

    @Override
    public Administrator getAdministratorById(int id) {
        return null;
    }

    @Override
    public Student getStudentById(int id) {
        return null;
    }
}
