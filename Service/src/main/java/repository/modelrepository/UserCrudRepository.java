package repository.modelrepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import repository.modelrepository.modelservices.crudrepositorislikeservice.AdministratorCrudRepository;
import repository.modelrepository.modelservices.crudrepositorislikeservice.StudentCrudRepository;
import repository.modelrepository.modelservices.crudrepositorislikeservice.TrainerCrudRepository;
import users.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository("Crud")
public class UserCrudRepository implements UserRepository{

    @Autowired
    private final StudentCrudRepository studentCrudRepository;
    @Autowired
    private final AdministratorCrudRepository administratorCrudRepository;
    @Autowired
    private final TrainerCrudRepository trainerCrudRepository;

    @Transactional
    @Override
    public Map<Integer, UserImpl> allUser() {
        Map<Integer, UserImpl> result = new HashMap<>();
        studentCrudRepository.findAll().forEach(user -> result.put(user.getId(), user));
        return result;
    }

    @Override
    public Map<Integer, UserImpl> allTrainer() {
        Map<Integer, UserImpl> result = new HashMap<>();
        trainerCrudRepository.findByrole(Role.TRAINER).forEach(user -> result.put(user.getId(), user));
        return result;
    }

    @Override
    public Map<Integer, UserImpl> allStudent() {
        Map<Integer, UserImpl> result = new HashMap<>();
        studentCrudRepository.findByrole(Role.STUDENT).forEach(user -> result.put(user.getId(), user));
        return result;
    }

    @Override
    public Map<Integer, UserImpl> allAdmin() {
        Map<Integer, UserImpl> result = new HashMap<>();
        administratorCrudRepository.findByrole(Role.ADMINISTRATOR).forEach(user -> result.put(user.getId(), user));
        return result;
    }

    @Override
    public UserImpl getUserById(Integer id) {
        return studentCrudRepository.findById(id).get();
    }

    @Override
    public int saveUser(UserImpl user) {
        if (user.getRole().equals(Role.ADMINISTRATOR))
            administratorCrudRepository.save((Administrator)user);
        else if (user.getRole().equals(Role.TRAINER))
            trainerCrudRepository.save((Trainer) user);
        else studentCrudRepository.save((Student) user);

        return user.getId();
    }

    @Override
    public Optional<UserImpl> removeUser(Integer id, String entity) {
        UserImpl userById = getUserById(id);
        studentCrudRepository.delete(userById);
        return Optional.of(userById);
    }

    @Override
    public UserImpl updateUser(UserImpl user) {
        studentCrudRepository.setUserByIdWithName(user.getId(), user.getName());
        return user;
    }

    @Override
    public Map<Integer, UserImpl> freeTrainer() {
        return null;
    }

    @Override
    public List<Student> studentFromGroup(Integer groupId) {
//     return studentCrudRepository.findByGroupId(groupId);
        return null;
    }

    @Override
    public Trainer getTrainerById(int id) {
       return trainerCrudRepository.findById(id).get();
    }

    @Override
    public Administrator getAdministratorById(int id) {
      return   administratorCrudRepository.findById(id).get();
    }

    @Override
    public Student getStudentById(int id) {
       return studentCrudRepository.findById(id).get();
    }
}
