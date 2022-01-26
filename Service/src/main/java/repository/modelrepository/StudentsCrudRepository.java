package repository.modelrepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import repository.StudentCrudRepository;
import users.Administrator;
import users.Student;
import users.Trainer;
import users.UserImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository("Crud")
public class StudentsCrudRepository implements UserRepository{
//
//    @Autowired
//    private StudentCrudRepository crudRepository;
    @PersistenceContext
    private final EntityManager em;

    @Transactional
    @Override
    public Map<Integer, UserImpl> allUser() {
  return null;
    }

    @Override
    public Map<Integer, UserImpl> allTrainer() {
        return null;
    }

    @Override
    public Map<Integer, UserImpl> allStudent() {
        Map<Integer, UserImpl> result = new HashMap<>();
//        crudRepository.findAll().forEach(user -> result.put(user.getId(), user));
        return result;
    }

    @Override
    public Map<Integer, UserImpl> allAdmin() {
        return null;
    }

    @Override
    public UserImpl getUserById(Integer id) {
        return null;
    }

    @Override
    public int saveUser(UserImpl user) {
        return 0;
    }

    @Override
    public Optional<UserImpl> removeUser(Integer id, String entity) {
        return Optional.empty();
    }

    @Override
    public UserImpl updateUser(UserImpl user) {
        return null;
    }

    @Override
    public Map<Integer, UserImpl> freeTrainer() {
        return null;
    }

    @Override
    public List<Student> studentFromGroup(Integer groupId) {
        return null;
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
