package Repository;

import Users.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepositoryImplInMemory implements UserRepository {
    private static UserRepositoryImplInMemory instance;
    public static Map <Integer, Trainer> userMap = new ConcurrentHashMap<>();

    private UserRepositoryImplInMemory() {
    }

    public static UserRepositoryImplInMemory getInstance() {
          if (instance == null) {
              synchronized (UserRepositoryImplInMemory.class) {
                  if (instance == null) {
                      instance = new UserRepositoryImplInMemory();
                  }
              }
        }
        return instance;
    }

    @Override
    public List<User> allUser() {
        return null;
    }

    @Override
    public List<Trainer> allTrainer() {
        return null;
    }

    @Override
    public List<Student> allStudent() {
        return null;
    }

    @Override
    public List<Administrator> allAdmin() {
        return null;
    }

    @Override
    public Optional<UserImpl> getUserById(Integer id) {
        return Optional.empty();
    }

    @Override
    public UserImpl saveUser(UserImpl user) {
        return null;
    }

    @Override
    public Optional<UserImpl> removeUser(Integer id) {
        return Optional.empty();
    }

}
