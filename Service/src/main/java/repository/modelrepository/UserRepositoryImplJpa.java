package repository.modelrepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import repository.modelrepository.modelfunction.jpaservicese.AdminServiceJpa;
import repository.modelrepository.modelfunction.jpaservicese.StudentServiceJpa;
import repository.modelrepository.modelfunction.jpaservicese.TrainerServiceJpa;
import repository.modelrepository.modelfunction.jpaservicese.UserServiceJpa;
import users.Administrator;
import users.Student;
import users.Trainer;
import users.UserImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@RequiredArgsConstructor
@Repository("Jpa")
public class UserRepositoryImplJpa implements UserRepository {

    @Autowired
    private final AdminServiceJpa adminServiceJpa;
    @Autowired
    private final StudentServiceJpa studentServiceJpa;
    @Autowired
    private final TrainerServiceJpa trainerServiceJpa;
    @Autowired
    private final UserServiceJpa userServiceJpa;

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
    public UserImpl getUserById(Integer id) {
        return userServiceJpa.getUserById(id);
    }

    @Override
    public int saveUser(UserImpl user) {
     return userServiceJpa.doSaveUser (user);
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
    public Trainer getTrainerById (int id) {
     return trainerServiceJpa.doGetTrainerById (id);
    }
    @Override
    public Administrator getAdministratorById (int id) {
        return adminServiceJpa.doGetAdministratorById (id);
    }
    @Override
    public Student getStudentById (int id) {
        return studentServiceJpa.doGetStudentById (id);
    }
}
