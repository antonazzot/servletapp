package springmvcconfig;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;

import javax.sql.DataSource;

@ComponentScan({"controller", "repository", "aspect"})
@Configuration
@EnableWebMvc
@RequiredArgsConstructor
@EnableAspectJAutoProxy
@PropertySource("classpath:app.properties")
@Import({RepositoryFactory.class, ThreadRepositoryFactory.class})
public class SpringConfig  implements WebMvcConfigurer {
    private final ApplicationContext applicationContext;
//    @Value("${postgres.driver}")
//    private final String DRIVER;
//    @Value("${postgres.name}")
//    private final String NAME;
//    @Value("${postgres.url}")
//    private final String URL;
//    @Value("${postgres.password}")
//    private final String PASSWORD;
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
//    public org.hibernate.cfg.Configuration configuration () {
//        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration().configure();
//        return configuration;
//    }
//
//    @Bean
//    public SessionFactory sessionFactory () {
//        return configuration().buildSessionFactory();
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
}