package Servlets;
import Servlets.DAO.DaoImp;
import Users.Administrator;
import Users.Student;
import Users.Trainer;
import Users.UserImpl;
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
    public UserActionServlet() {
    }

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

        new DaoImp();
        if (act.equalsIgnoreCase("add")) {
            this.checkRole(role, name, login, password, age);
            req.getRequestDispatcher("adminActList.jsp").forward(req, resp);
        }

    }

    private UserImpl checkRole(String role, String name, String login, String pass, int age) {
        Object user;
        if (role.equalsIgnoreCase("administrator")) {
            user = new Administrator(name, login, pass, age);
        } else if (role.equalsIgnoreCase("trainer")) {
            user = new Trainer(name, login, pass, age, new ArrayList());
        } else {
            user = new Student(name, login, pass, age, new HashMap());
        }

        return (UserImpl)user;
    }
}