package repository.springmvcconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Properties;

@ComponentScan({"controller", "repository", "aspect"})
@Configuration
@EnableWebMvc
@RequiredArgsConstructor
//@EnableWebSecurity
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableTransactionManagement
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJpaRepositories(basePackages = "repository", entityManagerFactoryRef = "factoryBean")
@PropertySource("classpath:app.properties")
@Import({RepositoryFactory.class, ThreadRepositoryFactory.class})
public class SpringConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;
    private final DataSource dataSource;

    //    @Value("${postgres.driver}")
//    private final String DRIVER;
//    @Value("${postgres.name}")
//    private final String NAME;
//    @Value("${postgres.url}")
//    private final String URL;
//    @Value("${postgres.password}")
//    private final String PASSWORD;

    @Primary
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

    @Bean
    public EntityManager em(@Autowired LocalContainerEntityManagerFactoryBean factoryBean) {
        return factoryBean.getNativeEntityManagerFactory().createEntityManager();
    }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver (){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        WebMvcConfigurer.super.addResourceHandlers(registry);
//        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/static/css");
//        registry.addResourceHandler("/static/**").addResourceLocations("/css");
//        registry.addResourceHandler("/webapp/resources/**").addResourceLocations("/resources/static/css");
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/.jsp").addResourceLocations("/WEB-INF/views/");
    }

    //    @Bean
//    public DataSource dataSource () {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(DRIVER);
//        dataSource.setUrl(URL);
//        dataSource.setUsername(NAME);
//        dataSource.setPassword(PASSWORD);
//
//        return  dataSource;
//    }

//    @Bean
//    public JdbcTemplate jdbcTemplate (@Autowired DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }

//    @Bean
//    public SpringResourceTemplateResolver templateResolver() {
//        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//        templateResolver.setApplicationContext(applicationContext);
//        templateResolver.setPrefix("/WEB-INF/views/");
//        templateResolver.setSuffix(".jsp");
//        return templateResolver;
//    }
//
//    @Bean
//    public SpringTemplateEngine templateEngine() {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver());
//        templateEngine.setEnableSpringELCompiler(true);
//        return templateEngine;
//    }
//
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
//        resolver.setTemplateEngine(templateEngine());
//        registry.viewResolver(resolver);
//    }
//@Override
//public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//
//        configurer.enable("/**");
//    configurer.enable();
//}
}