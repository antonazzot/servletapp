package servlets.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.RepositoryFactory;
import users.UserImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;

@WebFilter(urlPatterns = {"/checkUser", "/mvc/views/checkUser","/mvc/checkUser" })
public class StartFilter extends AbstractFilter {
    private static final Logger log = LoggerFactory.getLogger(StartFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getSession(false) == null || request.getSession(false).isNew()) {
            response.sendRedirect("/web/hello");
        }
        Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties"));

        UserImpl user;
        int id = 0;
        String login = null;
        boolean doesItLogin = false;
        // provides the ability to log in either by login or by id
        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (IllegalArgumentException e) {
            login = request.getParameter("id");
            log.info("Enter by login = {}", login);
            doesItLogin = true;
        }
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("remember_me");
        log.info("Enter by pass = {}", password);

        if (doesItLogin) {
            //Authentication by login
            user = checkUser(login, password);
        } else
            //Authentication by id
            user = checkUser(id, password);

        HttpSession session = request.getSession();
        session.setAttribute("repostype", properties.getProperty("repository.type"));
//        if (user != null) {
            session.setAttribute("user", user);
            log.info("SessionWithUserCreate = {}", user);
            if (rememberMe!=null && !rememberMe.equals("")) {
                Cookie[] cookies = request.getCookies();
                for (Cookie cookie :cookies) {
                    log.info("Cookie name={}", cookie.getName());
                    log.info("Cookie domain={}", cookie.getDomain());
                    log.info("Cookie path={}", cookie.getPath());
                    log.info("Cookie value={}", cookie.getValue());
                    log.info("Cookie version={}", cookie.getVersion());
                }

                session.setAttribute("cookie",cookies);
            }
        request.getRequestDispatcher("/mvc/checkUser").forward(request, response);
//        } else
////            response.sendRedirect(request.getContextPath()+"exception");
//            request.getRequestDispatcher("/mvc/exception").forward(request, response);
        filterChain.doFilter(request, response);
    }

    private UserImpl checkUser(int id, String password) {
        return itRightCondition(id, password);
    }

    private UserImpl checkUser(String login, String password) {
        return itRightCondition(login, password);
    }

    private UserImpl itRightCondition(int id, String password) {
        return RepositoryFactory.getRepository().allUser().values().stream()
                .filter(u -> u.getId() == id && u.getPassword().equals(password))
                .findFirst().orElse(null);
    }

    private UserImpl itRightCondition(String login, String password) {
        log.info("in start filter before check = {}", "login: " + login +"  " + "password: " +password );
        return RepositoryFactory.getRepository().allUser().values().stream()
                .filter(u -> u.getLogin().equals(login) && u.getPassword().equals(password))
                .findFirst().orElse(null);
    }
}
