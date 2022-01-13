package repository.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class SpringOrmConfig {

    @Autowired
    private DataSource dataSource;

    public SpringOrmConfig(@Autowired DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean factoryBean(@Autowired Properties jpaProperties) {
        LocalContainerEntityManagerFactoryBean containerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        containerEntityManagerFactoryBean.setDataSource(dataSource);
        containerEntityManagerFactoryBean.setPersistenceUnitName("jpa-unit");
        containerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        containerEntityManagerFactoryBean.setPackagesToScan("users", "threadmodel");
        containerEntityManagerFactoryBean.setJpaProperties(jpaProperties);

        return containerEntityManagerFactoryBean;
    }

    @Bean
    public Properties jpaProperties() {
        Properties properties = new Properties();
        properties.setProperty("dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
        properties.setProperty("show_sql", "true");
        properties.setProperty("hbm2ddl.auto", "none");
        properties.setProperty("current_session_context_class", "thread");
        properties.setProperty("connection.pool_size", "20");
        properties.setProperty("hibernate.dbcp.initialSize", "5");
        properties.setProperty("hibernate.dbcp.maxTotal", "20");
        properties.setProperty("hibernate.dbcp.maxIdle", "10");
        properties.setProperty("hibernate.dbcp.minIdle", "5");
        properties.setProperty("hibernate.dbcp.maxWaitMillis", "-1");
        properties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        return properties;
    }

    @Bean
    public TransactionManager transactionManager(@Autowired LocalContainerEntityManagerFactoryBean factoryBean) {

        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(factoryBean.getObject());
        return jpaTransactionManager;
    }
}
