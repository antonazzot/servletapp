package Servlets;
import DataBase.DataBaseInf;
import Servlets.DAO.DaoImp;
import ThreadModel.Group;
import ThreadModel.Theams;
import Users.Role;
import Users.Student;
import Users.UserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@WebServlet (value = "/checkUser")
public class CheckServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CheckServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserImpl user = (UserImpl) req.getSession().getAttribute("user");
        HttpSession session = req.getSession();

        if (Role.ADMINISTRATOR.equals(user.getRole())) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("adminActList.jsp");
            requestDispatcher.forward(req, resp);
        } else if (Role.STUDENT.equals(user.getRole())) {
                req.getRequestDispatcher("/studentservlet").forward(req, resp);
        } else if (Role.TRAINER.equals(user.getRole())) {
            Group group = DataBaseInf.groupHashMap.values()
                    .stream().filter(g -> g.getTrainer().getId() == user.getId()).findAny().get();
            Map<Integer, Student> studentHashMap = group.getStudentMap();
            Set<Theams> theams = group.getTheamsSet();
            session.setAttribute("group", group);
            req.setAttribute("set", theams);
            req.setAttribute("map", studentHashMap);
            req.getRequestDispatcher("trainerActList.jsp").forward(req, resp);
        }

    }
}
