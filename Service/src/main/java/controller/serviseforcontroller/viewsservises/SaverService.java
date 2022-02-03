package controller.serviseforcontroller.viewsservises;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import users.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class SaverService {
    @Autowired
    private  final PasswordEncoder passwordEncoder;
    public static UserImpl userForSave (String role, String name, String login, String password, int age) {
        Map<String, UserImpl> userMap = new HashMap<>(Map.of(
                "administrator", new Administrator()
                        .withRole(Role.ADMINISTRATOR)
                        .withName(name)
                        .withLogin(login)
                        .withPassword(  password)
                        .withAge(age),
                "trainer", new Trainer()
                        .withRole(Role.TRAINER)
                        .withName(name)
                        .withLogin(login)
                        .withPassword(password)
                        .withAge(age),
                "student", new Student()
                        .withRole(Role.STUDENT)
                        .withName(name)
                        .withLogin(login)
                        .withPassword(password)
                        .withAge(age)
        ));
        return userMap.get(role);
    }
}