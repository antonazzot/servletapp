package repository.springmvcconfig;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import repository.auth.UserService;

import javax.sql.DataSource;
@Configuration

public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/web/mvc","/WEB-INF/views/str.jsp").permitAll()
                .antMatchers("/web/mvc/views/**").authenticated()
                .antMatchers("/web/mvc/views/**").hasRole("ADMIN")
                .and().formLogin()
                .and().httpBasic()
                .and().logout().logoutSuccessUrl("/");
    }

    public DaoAuthenticationProvider daoAuthenticationProvider (@Autowired UserService userService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new JdbcUserDetailsManager(dataSource);
//    }
}
