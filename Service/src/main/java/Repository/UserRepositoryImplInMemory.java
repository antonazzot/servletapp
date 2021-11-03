package Repository;

import Action.IdFactory;
import DataBase.DataBaseInf;
import ThreadModel.Group;
import Users.Student;
import Users.Trainer;
import Users.UserImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

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
        HashMap <Integer, UserImpl> allUsers = new HashMap<>();
        allUsers.putAll(DataBaseInf.trainerHashMap);
        allUsers.putAll(DataBaseInf.studentHashMap);
        allUsers.putAll(DataBaseInf.adminHashMap);
        return allUsers;
    }

    @Override
    public HashMap<Integer, UserImpl> allTrainer() {
      return (HashMap<Integer, UserImpl>) DataBaseInf.trainerHashMap;
    }

    @Override
    public HashMap<Integer, UserImpl> allStudent() {
        return (HashMap<Integer, UserImpl>) DataBaseInf.studentHashMap;
    }

    @Override
    public HashMap<Integer, UserImpl> allAdmin() {
        return (HashMap<Integer, UserImpl>) DataBaseInf.adminHashMap;
    }

    @Override
    public UserImpl getUserById(Integer id) {
        return allUser().get(id);
    }

    @Override
    public int saveUser(UserImpl user) {
        user.withId(IdFactory.idBuilder());
        switch (user.getRole()) {
            case ADMINISTRATOR: DataBaseInf.adminHashMap.put(user.getId(), user);
            break;
            case TRAINER: {
                Trainer trainer = (Trainer) user;
                ((Trainer) user).withSalary(new ArrayList<>());
                DataBaseInf.trainerHashMap.put(user.getId(), trainer);}
            break;
            case STUDENT: {
                Student student = (Student)user;
                student.withTheamMark(new HashMap<>());
                DataBaseInf.studentHashMap.put(user.getId(), user);
            }
        }
        return user.getId();
    }

    @Override
    public Optional<UserImpl> removeUser(Integer id, String entity) {
        if (entity.equals("student") || entity.equals("trainer") || entity.equals("administrator")){
            entity = "user";
        }
        log.info("In remove method ={}", "ID: " + id + " Entity: " + entity);
        try {
            switch (entity) {
                case "user":
                {
                    UserImpl user = allUser().get(id);
                    switch (user.getRole()) {
                        case ADMINISTRATOR: DataBaseInf.adminHashMap.remove(id);
                            break;
                        case TRAINER: DataBaseInf.trainerHashMap.remove(id);
                            break;
                        case STUDENT: DataBaseInf.studentHashMap.remove(id);
                    }
                } break;
                case "group":
                {
                   DataBaseInf.groupHashMap.remove(id);
                } break;
                case "theam":
                {
                    DataBaseInf.theamsHashMap.remove(id);
                } break;
            }

        } catch (IllegalArgumentException e) {
            log.info("SQL EROR ={}", e.getMessage());
            e.printStackTrace();
        }
        return  Optional.empty();
    }

    @Override
    public UserImpl updateUser(UserImpl user) {
        UserImpl newUser = allUser().get(user.getId());
        newUser.withRole(user.getRole())
                .withName(user.getName())
                .withLogin(user.getLogin())
                .withPassword(user.getPassword())
                .withAge(user.getAge());
        return newUser;
    }

    @Override
    public HashMap<Integer, UserImpl> freeTrainer() {
        HashMap <Integer, UserImpl> result =  new HashMap<>(DataBaseInf.trainerHashMap);
        if (DataBaseInf.groupHashMap.values().isEmpty()) {
            return (HashMap<Integer, UserImpl>) DataBaseInf.trainerHashMap;
        }
        else  {

            ArrayList <UserImpl> busyTrainers = (ArrayList<UserImpl>) DataBaseInf.groupHashMap.values().stream()
                    .map(Group::getTrainer).collect(Collectors.toList());
            for (UserImpl trainer:
                DataBaseInf.trainerHashMap.values()) {
                for (UserImpl busyTrainer:
                    busyTrainers ) {
                    if (trainer.getId() == busyTrainer.getId())
                        result.remove(trainer.getId());
                }
            }
        }
        return  result;
    }

    @Override
    public ArrayList<UserImpl> studentFromGroup(Integer groupId) {
        Group group = DataBaseInf.groupHashMap.get(groupId);
        return new ArrayList<>(group.getStudentMap().values());
     }
}
