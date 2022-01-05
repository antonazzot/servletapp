package controller.serviseforcontroller.viewsservises;

import lombok.extern.slf4j.Slf4j;
import repository.RepositoryFactory;
import users.*;
@Slf4j
public class SaverService {
    public static UserImpl userForSave (String role, String name, String login, String password, int age) {
        UserImpl user;
        if (role.equalsIgnoreCase("administrator")) {
            user = new Administrator()
                    .withRole(Role.ADMINISTRATOR)
                    .withName(name)
                    .withLogin(login)
                    .withPassword(password)
                    .withAge(age);
            log.info("Administrator  add = {}", user);
        } else if (role.equalsIgnoreCase("trainer")) {
            user = new Trainer()
                    .withRole(Role.TRAINER)
                    .withName(name)
                    .withLogin(login)
                    .withPassword(password)
                    .withAge(age);
            log.info("returning value TrainerId ={}", user.getInf());
        } else {
            user = (Student) new Student()
                    .withRole(Role.STUDENT)
                    .withName(name)
                    .withLogin(login)
                    .withPassword(password)
                    .withAge(age);

            log.info("Student add = {}", user.getInf());
        }
        return user;
    }
}