package controller.serviseforcontroller.viewsservises;

import controller.serviseforcontroller.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import repository.RepositoryFactory;
import users.Role;
import users.Student;
import users.TempStudent;
@Service
@RequiredArgsConstructor
public class ActWithTempUser {
    private final SenderService senderService;
    private final String SUCCSESFUL_MESSAGE = "YOU ACCOUNT WAS ADD";
    private final String NOT_SUCCSESFUL_MESSAGE = "YOU ACCOUNT WAS DELETE";
    public  void doAct(String act, Integer[] id) {
        if(act.equals("add")) {
            for (Integer integer : id) {
                TempStudent tempUserById = RepositoryFactory.getRepository().getTempUserById(integer);
                senderService.sendMail(tempUserById.getGmail(), SUCCSESFUL_MESSAGE, "Dear" + tempUserById.getName() + "you was add in our system with login: " + tempUserById.getLogin() + "  , Have a nice day!" );
                Student student = (Student) new Student()
                        .withRole(Role.STUDENT)
                        .withName(tempUserById.getName())
                        .withLogin(tempUserById.getLogin())
                        .withPassword(tempUserById.getPassword())
                        .withAge(tempUserById.getAge());
                RepositoryFactory.getRepository().saveUser(student);
                RepositoryFactory.getRepository().removeTempStudent(integer);
            }

        }
        else
        {
            for (Integer integer : id) {
                RepositoryFactory.getRepository().removeTempStudent(integer);
            }

        }
    }
}
