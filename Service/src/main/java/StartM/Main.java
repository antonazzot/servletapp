package StartM;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext("repository");

//        UserRepository userRepository = ctx.getBean(UserRepository.class);
        UserRepository repository = RepositoryFactory.getRepository();
        System.out.println(repository);
        ThreadRepository repository1 = ThreadRepositoryFactory.getRepository();
        System.out.println(repository1);
    }
}
