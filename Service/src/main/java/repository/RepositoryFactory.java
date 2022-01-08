package repository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import repository.modelrepository.UserRepositoryImplJpa;
import repository.modelrepository.UserRepository;
import repository.modelrepository.UserRepositoryImplInMemory;
import repository.modelrepository.UserRepositoryImplPostgres;
import repository.threadmodelrep.ThreadRepositoryFactory;
import springmvcconfig.SpringConfig;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
@Configuration
@PropertySource({"classpath:app.properties"})
public class RepositoryFactory {
    @Value("${repository.type}")
    private  String repositoryName;

    private static UserRepository repository;

    @Autowired
    private  Map<String, UserRepository> repositoryMap;
    @PostConstruct
    public  void initMap () {
      RepositoryFactory.repository = repositoryMap.get(repositoryName);
    }

    public static UserRepository getRepository() {
        return repository;
    }

//    @Bean
//    public org.hibernate.cfg.Configuration configuration () {
//        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration().configure();
//        return configuration;
//    }
//
//    @Bean
//    public SessionFactory sessionFactory () {
//        return configuration().buildSessionFactory();
//    }

}
