package repository.modelrepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import repository.modelrepository.modelfunction.functionjpaerepositiry.AdminFunctionJpa;
import repository.modelrepository.modelfunction.functionjpaerepositiry.StudentFunctionJpa;
import repository.modelrepository.modelfunction.functionjpaerepositiry.TrainerFunctionJpa;
import repository.modelrepository.modelfunction.functionjpaerepositiry.UserFunctionJpa;
import users.Administrator;
import users.Student;
import users.Trainer;
import users.UserImpl;

import java.lang.module.Configuration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Repository("Jpa")
public class UserRepositoryImplJpa implements UserRepository {

    @Override
    public Map<Integer, UserImpl> allUser() {
       return UserFunctionJpa.getAllUser();
    }

    @Override
    public Map<Integer, UserImpl> allTrainer() {
       return TrainerFunctionJpa.getallTrainer();
    }

    @Override
    public Map<Integer, UserImpl> allStudent() {
        return StudentFunctionJpa.getAllStudent();
    }

    @Override
    public Map<Integer, UserImpl> allAdmin() {
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
    public Map<Integer, UserImpl> freeTrainer() {
      return TrainerFunctionJpa.freeTrainer();
    }

    @Override
    public List<Student> studentFromGroup(Integer groupId) {
        return StudentFunctionJpa.getStudentFromGroup(groupId);
    }
    @Override
    public Trainer getTrainerById (int id) {
     return TrainerFunctionJpa.doGetTrainerById (id);
    }
    @Override
    public Administrator getAdministratorById (int id) {
        return AdminFunctionJpa.doGetAdministratorById (id);
    }
    @Override
    public Student getStudentById (int id) {
        return StudentFunctionJpa.doGetStudentById (id);
    }
}
