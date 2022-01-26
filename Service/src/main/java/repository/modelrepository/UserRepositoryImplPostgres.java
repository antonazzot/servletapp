package repository.modelrepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import repository.modelrepository.modelfunction.postgresservices.AdminServicePostgres;
import repository.modelrepository.modelfunction.postgresservices.StudentServicePostgres;
import repository.modelrepository.modelfunction.postgresservices.TrainerServicePostgres;
import repository.modelrepository.modelfunction.postgresservices.UsersServicePostgres;
import users.Administrator;
import users.Student;
import users.Trainer;
import users.UserImpl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@Repository("Postgres")
@Slf4j
@RequiredArgsConstructor
public class UserRepositoryImplPostgres implements UserRepository {

//    private final JdbcTemplate jdbcTemplate;

    @Override
    public Map<Integer, UserImpl> allUser() {
        return UsersServicePostgres.allUser();
    }

    @Override
    public Map<Integer, UserImpl> allTrainer() {
        return TrainerServicePostgres.getallTrainer();
    }

    @Override
    public Map<Integer, UserImpl> allStudent() {
        return StudentServicePostgres.allStudent();
    }

    @Override
    public Map<Integer, UserImpl> allAdmin() {
        return AdminServicePostgres.allAdmin();
    }

    @Override
    public UserImpl getUserById(Integer id) {
        return UsersServicePostgres.getUserById(id);
    }

    @Override
    public int saveUser(UserImpl user) {
        return UsersServicePostgres.saveUser(user);
    }

    @Override
    public Optional<UserImpl> removeUser(Integer id, String entity) {
        return UsersServicePostgres.removeUser(id, entity);
    }

    @Override
    public UserImpl updateUser(UserImpl user) {
        return UsersServicePostgres.updateUser(user);
    }

    @Override
    public Map<Integer, UserImpl> freeTrainer() {
        return TrainerServicePostgres.freeTrainer();
    }

    @Override
    public List<Student> studentFromGroup(Integer groupId) {
        return StudentServicePostgres.studentFromGroup(groupId);
    }

    @Override
    public Trainer getTrainerById(int id) {
        return TrainerServicePostgres.getTrainerById(id);
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
