package repository.modelrepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import repository.modelrepository.modelservices.jpaservicese.*;
import users.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository("Jpa")
public class UserRepositoryImplJpa implements UserRepository {

    private final AdminServiceJpa adminServiceJpa;
    private final StudentServiceJpa studentServiceJpa;
    private final TrainerServiceJpa trainerServiceJpa;
    private final UserServiceJpa userServiceJpa;
    private final TempStudentService tempStudentService;

    @Override
    public Map<Integer, UserImpl> allUser() {
        return userServiceJpa.getAllUser();
    }

    @Override
    public Map<Integer, UserImpl> allTrainer() {
        return trainerServiceJpa.getallTrainer();
    }

    @Override
    public Map<Integer, UserImpl> allStudent() {
        return studentServiceJpa.getAllStudent();
    }

    @Override
    public Map<Integer, UserImpl> allAdmin() {
        return adminServiceJpa.getAllAdmin();
    }

    @Override
    public Map<Integer, TempStudent> allTemp() {
        return null;
    }

    @Override
    public UserImpl getUserById(Integer id) {
        return userServiceJpa.getUserById(id);
    }

    @Override
    public UserImpl getUserByLogin(String login) {
        return userServiceJpa.getUserByLogin(login);
    }

    @Override
    public int saveUser(UserImpl user) {
        return userServiceJpa.doSaveUser(user);
    }

    @Override
    public Optional<UserImpl> removeUser(Integer id, String entity) {
        return userServiceJpa.doRemoveUser(id, entity);
    }

    @Override
    public UserImpl updateUser(UserImpl user) {
        return userServiceJpa.doUpdateUser(user);
    }

    @Override
    public Map<Integer, UserImpl> freeTrainer() {
        return trainerServiceJpa.freeTrainer();
    }

    @Override
    public List<Student> studentFromGroup(Integer groupId) {
        return studentServiceJpa.getStudentFromGroup(groupId);
    }

    @Override
    public Trainer getTrainerById(int id) {
        return trainerServiceJpa.doGetTrainerById(id);
    }

    @Override
    public Administrator getAdministratorById(int id) {
        return adminServiceJpa.doGetAdministratorById(id);
    }

    @Override
    public Student getStudentById(int id) {
        return studentServiceJpa.doGetStudentById(id);
    }

    @Override
    public int saveTempStudent(TempStudent tempStudent) {
        return tempStudentService.doSaveTempstudent(tempStudent);
    }

    @Override
    public List<TempStudent> findAllTempSstudent() {
        return tempStudentService.getAllTempStudent();
    }

    @Override
    public TempStudent getTempUserById(Integer id) {
        return tempStudentService.doGetTempStudentById(id);
    }

    @Override
    public void removeTempStudent(Integer id) {
    tempStudentService.doRemoveTempstudent(id);
    }
}
