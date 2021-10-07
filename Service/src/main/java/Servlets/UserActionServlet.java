package Servlets;

import Servlets.DAO.DaoImp;
import Users.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet ("/UserActionServlet")
public class UserActionServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserActionServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String act = req.getParameter("act");
        String role = req.getParameter("role");
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        int age = 0;
        try {
            if (req.getParameter("age")!=null)
                age = Integer.parseInt( req.getParameter("id"));
        }
        catch (IllegalArgumentException e) {
            resp.getWriter().write("Введен неверный параметр");
        }

        DaoImp daoImp = new DaoImp();

        if (act.equalsIgnoreCase("add")){
            UserImpl user = checkRole(role, name, login, password, age);
            req.getRequestDispatcher("adminActList.jsp").forward(req, resp);
            logger.info("add user = {role, name, login, age}", role, name, login, age);
        }
    }

    private UserImpl checkRole(String role, String name, String login, String pass, int age ) {
        UserImpl user;
        if (role.equalsIgnoreCase("administrator")){
           user = new Administrator(name, login, pass,  age);
        }
        else
            if (role.equalsIgnoreCase("trainer"))
            {
                user = new Trainer(name, login, pass,  age, new ArrayList<>());
            }
            else  user = new Student(name, login, pass,  age, new HashMap<>());
            return user;
    }
}
