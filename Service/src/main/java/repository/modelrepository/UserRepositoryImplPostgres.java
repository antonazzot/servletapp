package repository.modelrepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import repository.modelrepository.modelservices.postgresservices.AdminServicePostgres;
import repository.modelrepository.modelservices.postgresservices.StudentServicePostgres;
import repository.modelrepository.modelservices.postgresservices.TrainerServicePostgres;
import repository.modelrepository.modelservices.postgresservices.UsersServicePostgres;
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
    @Autowired
    private final AdminServicePostgres adminServicePostgres;
    @Autowired
    private final TrainerServicePostgres trainerServicePostgres;
    @Autowired
    private final StudentServicePostgres studentServicePostgres;
    @Autowired
    private final UsersServicePostgres usersServicePostgres;

    @Override
    public Map<Integer, UserImpl> allUser() {
        return usersServicePostgres.allUser();
    }

    @Override
    public Map<Integer, UserImpl> allTrainer() {
        return trainerServicePostgres.getallTrainer();
    }

    @Override
    public Map<Integer, UserImpl> allStudent() {
        return studentServicePostgres.allStudent();
    }

    @Override
    public Map<Integer, UserImpl> allAdmin() {
        return adminServicePostgres.allAdmin();
    }

    @Override
    public UserImpl getUserById(Integer id) {
        return usersServicePostgres.getUserById(id);
    }

    @Override
    public UserImpl getUserByLogin(String login) {
        return usersServicePostgres.getUserByLogin(login);
    }

    @Override
    public int saveUser(UserImpl user) {
        return usersServicePostgres.saveUser(user);
    }

    @Override
    public Optional<UserImpl> removeUser(Integer id, String entity) {
        return usersServicePostgres.removeUser(id, entity);
    }

    @Override
    public UserImpl updateUser(UserImpl user) {
        return usersServicePostgres.updateUser(user);
    }

    @Override
    public Map<Integer, UserImpl> freeTrainer() {
        return trainerServicePostgres.freeTrainer();
    }

    @Override
    public List<Student> studentFromGroup(Integer groupId) {
        return studentServicePostgres.studentFromGroup(groupId);
    }

    @Override
    public Trainer getTrainerById(int id) {
        return trainerServicePostgres.getTrainerById(id);
    }

    @Override
    public Administrator getAdministratorById(int id) {
     return    adminServicePostgres.getAdminById(id) ;
    }

    @Override
    public Student getStudentById(int id)  {
      return   studentServicePostgres.getStudentById(id);
    }

}
