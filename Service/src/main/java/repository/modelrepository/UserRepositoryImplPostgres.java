package repository.modelrepository;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import repository.modelrepository.modelfunction.functionpostgress.AdminFunctionPostgres;
import repository.modelrepository.modelfunction.functionpostgress.StudentFunctionPostgres;
import repository.modelrepository.modelfunction.functionpostgress.TrainerFunctionPostgres;
import repository.modelrepository.modelfunction.functionpostgress.UsersFunctionPostgres;
import users.Administrator;
import users.Student;
import users.Trainer;
import users.UserImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@Repository("Postgres")
@Slf4j
public class UserRepositoryImplPostgres implements UserRepository {

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
    public int saveUser(@Valid UserImpl user) {
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
