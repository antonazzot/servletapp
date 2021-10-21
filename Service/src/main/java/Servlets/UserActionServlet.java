package Servlets;

import Action.IdFactory;
import Repository.RepositoryFactory;
import Users.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 This servlet  creates some user depending on the selected role
 **/

@WebServlet({"/UserActionServlet"})
public class UserActionServlet extends HttpServlet {
    static final Logger log = LoggerFactory.getLogger(UserActionServlet.class);
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String role = req.getParameter("role");
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        int age =0;
        try {age = Integer.parseInt(req.getParameter("age"));
                    } catch (IllegalArgumentException e) {
            req.getRequestDispatcher("exeception.jsp").forward(req, resp);
        }
            // add some user
            checkRole(role, name, login, password, age);
            req.getRequestDispatcher("adminControl/adminActList.jsp").forward(req, resp);

    }

    private void checkRole(String role, String name, String login, String pass, int age) {
        UserImpl user;
        if (role.equalsIgnoreCase("administrator")) {
            user = new Administrator()
                    .withRole(Role.ADMINISTRATOR)
                    .withName(name)
                    .withLogin(login)
                    .withPassword(pass)
                    .withAge(age);
            RepositoryFactory.getRepository().saveUser(user);

            log.info("Administrator  add = {}", user);
        } else if (role.equalsIgnoreCase("trainer")) {
            user = new Trainer()
                    .withRole(Role.TRAINER)
                    .withName(name)
                    .withLogin(login)
                    .withPassword(pass)
                    .withAge(age);
            RepositoryFactory.getRepository().saveUser(user);
            log.info("Trainer  add = {}", user);
        } else {
            user = new Student()
                    .withRole(Role.STUDENT)
                    .withName(name)
                    .withLogin(login)
                    .withPassword(pass)
                    .withAge(age);
            RepositoryFactory.getRepository().saveUser(user);
            log.info("Student add = {}", user);
        }
    }
}