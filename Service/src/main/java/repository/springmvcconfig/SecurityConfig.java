package repository.springmvcconfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebMvc
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/mvc/str").permitAll()
                .antMatchers("/mvc/views/**").authenticated()
                .antMatchers("/mvc/views/**").hasAuthority("ROLE_ADMINISTRATOR")
                .antMatchers("/mvc/trainer/**").authenticated()
                .antMatchers("/mvc/trainer/**").hasRole("TRAINER")
                .antMatchers("TrainerControlPage/trainerstartpage").hasRole("TRAINER")
                .antMatchers("TrainerControlPage/*").hasRole("TRAINER")
                .antMatchers("/trainerstartpage.jsp").hasRole("TRAINER")
                .antMatchers("/mvc/trainer/traineract").hasRole("TRAINER")
                .antMatchers("/mvc/student").authenticated()
                .antMatchers("/mvc/student").hasRole("STUDENT")
                .and().formLogin().successForwardUrl("/mvc/checkUser")
                .and().httpBasic()
                .and().logout().logoutSuccessUrl("/mvc/str")
                .and().csrf().disable();
        ;

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(@Autowired UserDetailsService userService,
                                                               @Autowired PasswordEncoder passwordEncoder) {
        log.info("user!ser: ={}", userService.toString());
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new JdbcUserDetailsManager(dataSource);
//    }
}
