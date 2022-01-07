package repository.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class EntityManagerConfig {

    @Bean
    public org.hibernate.cfg.Configuration configuration () {
        return new org.hibernate.cfg.Configuration().configure();
    }

    @Bean
    public SessionFactory sessionFactory (@Autowired org.hibernate.cfg.Configuration configuration) {
        return configuration.buildSessionFactory();
    }
}
