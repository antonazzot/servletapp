package Servlets;

import Repository.RepositoryFactory;
import Users.Role;
import Users.UserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 Servlet  provides  information to JSP
 for change user data
 **/
@WebServlet("/changeUserServlet")
public class ActionChangeServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(TrainerActServlet.class);
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        UserImpl user = RepositoryFactory.getRepository().getUserById(id);

        String role = req.getParameter("role");
        String login = req.getParameter("login").equals("") ? user.getLogin():req.getParameter("login");
        String pass = req.getParameter("pass").equals("") ? user.getPassword():req.getParameter("pass");
        String name = req.getParameter("name").equals("") ? user.getName():req.getParameter("name");
        int age = req.getParameter("age").equals("") ? user.getAge():Integer.parseInt(req.getParameter("age"));

        user.withRole(Role.valueOf(role.toUpperCase()))
                .withId(id)
                .withName(name)
                .withLogin(login)
                .withPassword(pass)
                .withAge(age);


        RepositoryFactory.getRepository().updateUser(user);
        log.info("UserUpdate = {}", id+"  "+role+"  "+name+"  "+login+"  "+pass+"  "+age);
        req.getRequestDispatcher("adminControl/adminActList.jsp").forward(req, resp);
    }
}