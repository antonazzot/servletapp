package StartM;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repository.RepositoryFactory;
import repository.modelrepository.UserRepository;
import repository.modelrepository.UserRepositoryImplJpa;
import repository.modelrepository.modelfunction.functionjpaerepositiry.UserFunctionJpa;
import repository.threadmodelrep.ThreadRepository;
import repository.threadmodelrep.ThreadRepositoryFactory;
import users.Administrator;
import users.Role;
import users.Student;
import users.UserImpl;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("repository");

//        UserRepository userRepository = ctx.getBean(UserRepository.class);
//        UserRepository repository = RepositoryFactory.getRepository();
        RepositoryFactory.getRepository().allUser().values().stream()
                .map(UserImpl::getInf).forEach(System.out::println);

//        System.out.println("---------------->>>>>>>>"+repository);
//        repository.allStudent().values().stream().map(s->s.getInf()+"***************").forEach(System.out::println);
//
//         UserFunctionJpa.getAllUser().values().stream().map(s->s.getInf()).forEach(System.out::println);
//        UserImpl user = RepositoryFactory.getRepository().allUser().values().stream()
//                .filter(u -> u.getLogin().equals("Admin") && u.getPassword().equals("pass"))
//                .findFirst().orElse(null);
//        UserImpl user1 = new UserImpl().withName("name").withLogin("login").withPassword("passs").withAge(130);
//        RepositoryFactory.getRepository().saveUser(user1);
//        System.out.println(user.getInf());


//        Administrator administrator = (Administrator) new Administrator()
//                .withRole(Role.ADMINISTRATOR)
//                .withName("Anton")
//                .withLogin("Admin")
//                .withPassword("pass")
//                .withAge(34);
//        if (RepositoryFactory.getRepository().allUser().values().stream()
//                .filter(user -> user.getLogin()!=null
//                        &&  user.getName()!=null
//                        &&  user.getPassword()!=null)
//                .noneMatch(u -> u.getLogin().equals(administrator.getLogin()) &&
//                        u.getPassword().equals(administrator.getPassword()) &&
//                        u.getName().equals(administrator.getName()) &&
//                        u.getAge() == administrator.getAge())
//        )
//            System.out.println("++++++++++++");
//    }
    }
}
