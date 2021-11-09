package repository.modelrepository.modelfunction.functionunmemory;

import action.IdFactory;
import database.DataBaseInf;
import lombok.extern.slf4j.Slf4j;
import users.Student;
import users.Trainer;
import users.UserImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
public class UsersFunctionMemory {
    public static HashMap<Integer, UserImpl> getallUser() {
        HashMap<Integer, UserImpl> allUsers = new HashMap<>();
        allUsers.putAll(DataBaseInf.getTrainerHashMap());
        allUsers.putAll(DataBaseInf.getStudentHashMap());
        allUsers.putAll(DataBaseInf.getAdminHashMap());
        return allUsers;
    }

    public static UserImpl getUserById(Integer id) {
        return getallUser().get(id);
    }

    public static int saveUser(UserImpl user) {
        user.withId(IdFactory.idBuilder());
        switch (user.getRole()) {
            case ADMINISTRATOR:
                DataBaseInf.getAdminHashMap().put(user.getId(), user);
                break;
            case TRAINER: {
                Trainer trainer = (Trainer) user;
                ((Trainer) user).withSalary(new ArrayList<>());
                DataBaseInf.getTrainerHashMap().put(user.getId(), trainer);
            }
            break;
            case STUDENT: {
                Student student = (Student) user;
                student.withTheamMark(new HashMap<>());
                DataBaseInf.getStudentHashMap().put(user.getId(), user);
            }
        }
        return user.getId();
    }

    public static Optional<UserImpl> doremoveUser(Integer id, String entity) {
        if (entity.equals("student") || entity.equals("trainer") || entity.equals("administrator")) {
            entity = "user";
        }
        log.info("In remove method ={}", "ID: " + id + " Entity: " + entity);
        try {
            switch (entity) {
                case "user": {
                    UserImpl user = getallUser().get(id);
                    switch (user.getRole()) {
                        case ADMINISTRATOR:
                            DataBaseInf.getAdminHashMap().remove(id);
                            break;
                        case TRAINER:
                            DataBaseInf.getTrainerHashMap().remove(id);
                            break;
                        case STUDENT:
                            DataBaseInf.getStudentHashMap().remove(id);
                    }
                }
                break;
                case "group": {
                    DataBaseInf.getGroupHashMap().remove(id);
                }
                break;
                case "theam": {
                    DataBaseInf.getTheamsHashMap().remove(id);
                }
                break;
            }

        } catch (IllegalArgumentException e) {
            log.info("SQL EROR ={}", e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static UserImpl doupdateUser(UserImpl user) {
        UserImpl newUser = getallUser().get(user.getId());
        newUser.withRole(user.getRole())
                .withName(user.getName())
                .withLogin(user.getLogin())
                .withPassword(user.getPassword())
                .withAge(user.getAge());
        return newUser;
    }


}
