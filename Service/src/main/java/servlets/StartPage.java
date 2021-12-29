package servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repository.RepositoryFactory;
import repository.modelrepository.UserRepository;
import users.Administrator;
import users.Role;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;

/**
 * It's start servlet
 * witch create user with Admin role by context inf,
 * and make some session revision
 **/
@WebServlet("/hello")
public class StartPage extends HttpServlet {
    Logger log = LoggerFactory.getLogger(StartPage.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        {
            ServletContext servletContext = getServletContext();
            String adminLogin = servletContext.getInitParameter("AdminLogin");
            String adminPassword = servletContext.getInitParameter("AdminPassword");
            HttpSession session = req.getSession();
            Properties properties = new Properties();
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("app.properties"));
//            ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext("repository");
//            UserRepository bean = ctx.getBean("userRepository",UserRepository.class);
            {
                Administrator administrator = (Administrator) new Administrator()
                        .withRole(Role.ADMINISTRATOR)
                        .withName("Anton")
                        .withLogin(adminLogin)
                        .withPassword(adminPassword)
                        .withAge(34);
                log.info("Types of using memory ={}", properties.getProperty("repository.type"));
               log.info("Repository ={}", RepositoryFactory.getRepository().getClass().getName());
                if (RepositoryFactory.getRepository().allUser().values().stream()
                        .noneMatch(u -> u.getLogin().equals(administrator.getLogin()) &&
                                u.getPassword().equals(administrator.getPassword()) &&
                                u.getName().equals(administrator.getName()) &&
                                u.getAge() == administrator.getAge())
                )
                    RepositoryFactory.getRepository().saveUser(administrator);
                log.info("Admin = {}", administrator.getInf());
            }

            if (session.getAttribute("user") != null) {
                req.getRequestDispatcher("/checkUser").forward(req, resp);
            } else {
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("hello.jsp");
                requestDispatcher.forward(req, resp);
            }
        }

    }
}

