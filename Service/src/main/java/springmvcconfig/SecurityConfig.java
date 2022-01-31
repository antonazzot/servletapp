//package springmvcconfig;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//
//import javax.sql.DataSource;
//
//@RequiredArgsConstructor
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private final DataSource dataSource;
//    @Bean
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/","/web", "/web/mvc", "/web/mvc/hello", "/mvc"
//                ,"/WEB-INF/views/str.jsp", "/WEB-INF/views/str", "/views/str.jsp", "/views/str", "/str.jsp")
//                .permitAll()
//                .and().formLogin().permitAll();
//    }
//
//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        return new JdbcUserDetailsManager(dataSource);
//    }
//}
