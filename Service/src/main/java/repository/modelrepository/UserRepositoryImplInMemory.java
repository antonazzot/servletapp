package repository.modelrepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import repository.modelrepository.modelservices.memoryservices.AdminServiceMemory;
import repository.modelrepository.modelservices.memoryservices.StudentServiceMemory;
import repository.modelrepository.modelservices.memoryservices.TrainerServiceMemory;
import repository.modelrepository.modelservices.memoryservices.UsersServiceMemory;
import users.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository("Memory")
@Slf4j
public class UserRepositoryImplInMemory implements UserRepository {

    @Override
    public Map<Integer, UserImpl> allUser() {
        return UsersServiceMemory.getallUser();
    }

    @Override
    public Map<Integer, UserImpl> allTrainer() {
        return TrainerServiceMemory.getallTrainer();
    }

    @Override
    public Map<Integer, UserImpl> allStudent() {
        return StudentServiceMemory.getallStudent();
    }

    @Override
    public Map<Integer, UserImpl> allAdmin() {
        return AdminServiceMemory.getallAdmin();
    }

    @Override
    public Map<Integer, TempStudent> allTemp() {
        return null;
    }

    @Override
    public UserImpl getUserById(Integer id) {
        return UsersServiceMemory.getUserById(id);
    }

    @Override
    public UserImpl getUserByLogin(String login) {
        return UsersServiceMemory.getUserByLogin(login);
    }

    @Override
    public int saveUser(UserImpl user) {
        return UsersServiceMemory.saveUser(user);
    }

    @Override
    public Optional<UserImpl> removeUser(Integer id, String entity) {
        return UsersServiceMemory.doremoveUser(id, entity);
    }

    @Override
    public UserImpl updateUser(UserImpl user) {
        return UsersServiceMemory.doupdateUser(user);
    }

    @Override
    public Map<Integer, UserImpl> freeTrainer() {
        return TrainerServiceMemory.getfreeTrainer();
    }

    @Override
    public List<Student> studentFromGroup(Integer groupId) {
        return StudentServiceMemory.studentFromGroup(groupId);
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

    @Override
    public int saveTempStudent(TempStudent tempStudent) {
        return 0;
    }

    @Override
    public List<TempStudent> findAllTempSstudent() {
        return null;
    }

    @Override
    public TempStudent getTempUserById(Integer id) {
        return null;
    }

    @Override
    public void removeTempStudent(Integer id) {

    }
}
