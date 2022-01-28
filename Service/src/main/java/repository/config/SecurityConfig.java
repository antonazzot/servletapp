package repository.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private final DataSource dataSource;
    @Bean
    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.userDetailsService(userDetailsService())
//                .authorizeRequests()
//                .antMatchers("/", "/web", "/web/mvc", "/web/mvc/hell", "/WEB-INF/views/",
//                        "/views/str", "/str.jsp", "/WEB-INF/views/str.jsp","/WEB-INF/views/str", "/str"
//                ).permitAll()
////                .antMatchers("/views/*").hasRole("ADMIN")
//                .and().formLogin().permitAll()
////                .anyRequest().anonymous()
////                .and().formLogin()
////                .loginPage( "/web/mvc/hell").permitAll()
//                .and().logout().permitAll();
        http.authorizeRequests().antMatchers("/", "/web", "/web/mvc", "/web/mvc/hell", "/WEB-INF/views/",
                "/views/str", "/str.jsp", "/WEB-INF/views/str.jsp","/WEB-INF/views/str", "/str"
        ).permitAll();
////                .anyRequest().permitAll()
////                .antMatchers("/web", "/web/mvc", "/web/mvc/hell").permitAll()
////                .antMatchers("/adminAct").authenticated()
//////                .antMatchers("/views/*").hasRole("ADMIN")
////                .and().formLogin()
////                .loginPage("/web/mvc/hell").permitAll()
////                .and().logout().logoutSuccessUrl("/");
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new JdbcUserDetailsManager(dataSource);
    }
}
