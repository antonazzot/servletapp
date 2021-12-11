package repository.modelrepository;

import users.Administrator;
import users.Student;
import users.Trainer;
import users.UserImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserRepository {
    Map<Integer, UserImpl> allUser();

    Map<Integer, UserImpl> allTrainer();

    Map<Integer, UserImpl> allStudent();

    Map<Integer, UserImpl> allAdmin();

    UserImpl getUserById(Integer id);

    int saveUser(UserImpl user);

    Optional<UserImpl> removeUser(Integer id, String entity);

    UserImpl updateUser(UserImpl user);

    Map<Integer, UserImpl> freeTrainer();

    List<Student> studentFromGroup(Integer groupId);

    Trainer getTrainerById (int id);

    Administrator getAdministratorById (int id);

    Student getStudentById (int id);

}
