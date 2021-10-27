package Repository;

import Users.*;

import java.util.ArrayList;
import java.util.HashMap;
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
    public HashMap<Integer, UserImpl> allUser() {
        return null;
    }

    @Override
    public HashMap<Integer, UserImpl> allTrainer() {
        return null;
    }

    @Override
    public HashMap<Integer, UserImpl> allStudent() {
        return null;
    }

    @Override
    public HashMap<Integer, UserImpl> allAdmin() {
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
    public HashMap<Integer, UserImpl> freeTrainer() {
        return null;
    }

    @Override
    public ArrayList< UserImpl> studentFromGroup(Integer groupId) {
        return null;
    }

    @Override
    public UserImpl getUserByParam(String name, String login, String pass, int age) {
        return null;
    }

}
