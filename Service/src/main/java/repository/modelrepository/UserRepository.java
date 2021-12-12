package repository.modelrepository;

import jakarta.validation.Valid;
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

    int saveUser(@Valid UserImpl user);

    Optional<UserImpl> removeUser(Integer id, String entity);

    UserImpl updateUser(@Valid UserImpl user);

    Map<Integer, UserImpl> freeTrainer();

    List<Student> studentFromGroup(Integer groupId);

    Trainer getTrainerById (int id);

    Administrator getAdministratorById (int id);

    Student getStudentById (int id);

}
