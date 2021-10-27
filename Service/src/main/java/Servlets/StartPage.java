package Servlets;

import DataBase.DataBaseInf;
import DAO.DataBaseConnection;
import Repository.RepositoryFactory;
import Users.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 It's start servlet
 witch create user with Admin role by context inf,
 and make some session revision
 **/
@WebServlet("/hello")
public class StartPage extends HttpServlet {
    Logger log = LoggerFactory.getLogger(StartPage.class);
    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
           {
            ServletContext servletContext = getServletContext();
            String adminLogin = servletContext.getInitParameter("AdminLogin");
            String adminPassword = servletContext.getInitParameter("AdminPassword");
            HttpSession session = req.getSession();
             {
               Administrator administrator = (Administrator) new Administrator()
                       .withRole(Role.ADMINISTRATOR)
                       .withName("Anton")
                       .withLogin("admin")
                       .withPassword("pass")
                       .withAge(34);
               if (RepositoryFactory.getRepository().allUser().values().stream()
                     .noneMatch(u -> u.getLogin().equals(administrator.getLogin()) &&
                             u.getPassword().equals(administrator.getPassword()) &&
                             u.getName().equals(administrator.getName()) &&
                             u.getAge()==administrator.getAge())
                )
                RepositoryFactory.getRepository().saveUser(administrator);
             }
            log.info("Admin = {}", "Pass ={}", adminLogin, adminPassword);

            if (session.getAttribute("user")!=null)  {
            UserImpl user = (UserImpl) session.getAttribute("user");
            req.getRequestDispatcher("/checkUser").forward(req, resp);
                }
            else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("hello.jsp");
            requestDispatcher.forward(req, resp);}
    }

}
}

