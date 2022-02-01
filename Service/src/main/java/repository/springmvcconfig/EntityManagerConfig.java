package repository.springmvcconfig;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@PropertySource("classpath:app.properties")
@Configuration
@RequiredArgsConstructor
public class EntityManagerConfig {
    @Value("${postgres.driver}")
    private String DRIVER;
    @Value("${postgres.name}")
    private String NAME;
    @Value("${postgres.url}")
    private String URL;
    @Value("${postgres.password}")
    private String PASSWORD;
    @Bean
    public org.hibernate.cfg.Configuration configuration () {
        return new org.hibernate.cfg.Configuration().configure();
    }

    @Bean
    public SessionFactory sessionFactory (@Autowired org.hibernate.cfg.Configuration configuration) {
        return configuration.buildSessionFactory();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(URL);
        dataSource.setUsername(NAME);
        dataSource.setPassword(PASSWORD);

        return dataSource;
    }
}
