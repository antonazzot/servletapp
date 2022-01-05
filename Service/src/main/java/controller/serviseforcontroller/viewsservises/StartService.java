package controller.serviseforcontroller.viewsservises;

import lombok.extern.slf4j.Slf4j;
import org.apache.taglibs.standard.extra.spath.Predicate;
import repository.RepositoryFactory;
import users.Administrator;
import users.Role;

import java.io.IOException;
import java.util.Properties;

@Slf4j
public class StartService {

    public static void initAdmin(){
        {
            String adminLogin = "Admin";
            String adminPassword = "pass";
            Properties properties = new Properties();
            try {
                properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties"));
                adminLogin = properties.getProperty("adminLogin");
                adminPassword = properties.getProperty("adminPassword");
            } catch (IOException e) {
                e.printStackTrace();
            }

            Administrator administrator = (Administrator) new Administrator()
                    .withRole(Role.ADMINISTRATOR)
                    .withName("Anton")
                    .withLogin(adminLogin)
                    .withPassword(adminPassword)
                    .withAge(34);
            log.info("Types of using memory ={}", properties.getProperty("repository.type"));
            log.info("Repository ={}", RepositoryFactory.getRepository().getClass().getName());

            if ( RepositoryFactory.getRepository().allAdmin().isEmpty() ||
                    RepositoryFactory.getRepository().allAdmin().values().stream()
                            .filter(user -> user.getLogin()!=null
                                    &&  user.getName()!=null
                                    &&  user.getPassword()!=null)
                            .noneMatch(u -> u.getLogin().equals(administrator.getLogin()) &&
                                    u.getPassword().equals(administrator.getPassword()) &&
                                    u.getName().equals(administrator.getName()) &&
                                    u.getAge() == administrator.getAge())) {
                RepositoryFactory.getRepository().saveUser(administrator);
            }
            log.info("Admin = {}", administrator.getInf());
        }
    }
}