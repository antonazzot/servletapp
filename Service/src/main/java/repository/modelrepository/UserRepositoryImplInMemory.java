package repository.modelrepository;

import lombok.extern.slf4j.Slf4j;
import repository.modelrepository.modelfunction.functionunmemory.AdminFunctionMemory;
import repository.modelrepository.modelfunction.functionunmemory.StudentFunctionMemory;
import repository.modelrepository.modelfunction.functionunmemory.TrainerFunctionMemory;
import repository.modelrepository.modelfunction.functionunmemory.UsersFunctionMemory;
import users.UserImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
public class UserRepositoryImplInMemory implements UserRepository {
    private static UserRepositoryImplInMemory instance;
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
     return UsersFunctionMemory.getallUser();
    }

    @Override
    public HashMap<Integer, UserImpl> allTrainer() {
      return TrainerFunctionMemory.getallTrainer();
    }

    @Override
    public HashMap<Integer, UserImpl> allStudent() {
        return StudentFunctionMemory.getallStudent();
    }

    @Override
    public HashMap<Integer, UserImpl> allAdmin() {
        return AdminFunctionMemory.getallAdmin();
    }

    @Override
    public UserImpl getUserById(Integer id) {
        return UsersFunctionMemory.getUserById(id);
    }

    @Override
    public int saveUser(UserImpl user) {
      return UsersFunctionMemory.saveUser(user);
    }

    @Override
    public Optional<UserImpl> removeUser(Integer id, String entity) {
      return UsersFunctionMemory.doremoveUser(id,entity);
    }

    @Override
    public UserImpl updateUser(UserImpl user) {
      return   UsersFunctionMemory.doupdateUser(user);
    }

    @Override
    public HashMap<Integer, UserImpl> freeTrainer() {
        return TrainerFunctionMemory.getfreeTrainer();
    }

    @Override
    public ArrayList<UserImpl> studentFromGroup(Integer groupId) {
      return StudentFunctionMemory.studentFromGroup(groupId);
     }
}
