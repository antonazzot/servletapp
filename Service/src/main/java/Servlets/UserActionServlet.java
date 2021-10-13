package Servlets;

import Servlets.DAO.DaoImp;
import Users.Administrator;
import Users.Student;
import Users.Trainer;
import Users.UserImpl;
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

@WebServlet({"/UserActionServlet"})
public class UserActionServlet extends HttpServlet {
    static final Logger log = LoggerFactory.getLogger(UserActionServlet.class);
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String act = req.getParameter("act");
        String role = req.getParameter("role");
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        int age = 0;
        try {
            if (req.getParameter("age") != null) {
                age = Integer.parseInt(req.getParameter("id"));
            }
        } catch (IllegalArgumentException var11) {
            resp.getWriter().write("Введен неверный параметр");
        }
        if (act.equalsIgnoreCase("add")) {
            this.checkRole(role, name, login, password, age);
            req.getRequestDispatcher("adminActList.jsp").forward(req, resp);
        }
    }

    private void checkRole(String role, String name, String login, String pass, int age) {
        UserImpl user;
        if (role.equalsIgnoreCase("administrator")) {
            user = new Administrator(name, login, pass, age);
            log.info("User = {}", user);
        } else if (role.equalsIgnoreCase("trainer")) {
            user = new Trainer(name, login, pass, age, new ArrayList());
            log.info("User = {}", user);
        } else {
            user = new Student(name, login, pass, age, new HashMap());
            log.info("User = {}", user);
        }
    }
}