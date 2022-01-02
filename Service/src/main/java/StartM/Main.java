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
import repository.threadmodelrep.ThreadRepository;
import repository.threadmodelrep.ThreadRepositoryFactory;
import users.UserImpl;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
       ApplicationContext ctx = new AnnotationConfigApplicationContext ("springmvcconfig","repository");

//        UserRepository userRepository = ctx.getBean(UserRepository.class);
        UserRepository repository = RepositoryFactory.getRepository();
        System.out.println("---------------->>>>>>>>"+repository);
        repository.allStudent().values().stream().map(s->s.getInf()).forEach(System.out::println);

    }
}
