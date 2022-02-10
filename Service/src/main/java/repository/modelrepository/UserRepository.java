package repository.modelrepository;

import users.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserRepository {
    Map<Integer, UserImpl> allUser();

    Map<Integer, UserImpl> allTrainer();

    Map<Integer, UserImpl> allStudent();

    Map<Integer, UserImpl> allAdmin();

    Map<Integer, TempStudent> allTemp();

    UserImpl getUserById(Integer id);

    UserImpl getUserByLogin (String login);

    int saveUser(UserImpl user);

    Optional<UserImpl> removeUser(Integer id, String entity);

    UserImpl updateUser( UserImpl user);

    Map<Integer, UserImpl> freeTrainer();

    List<Student> studentFromGroup(Integer groupId);

    Trainer getTrainerById (int id);

    Administrator getAdministratorById (int id);

    Student getStudentById (int id);

    int saveTempStudent (TempStudent tempStudent);

    List <TempStudent> findAllTempSstudent();

    TempStudent getTempUserById (Integer id);

    void removeTempStudent (Integer id);

}
