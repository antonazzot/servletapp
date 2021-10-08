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
    private static final Logger logger =  LoggerFactory.getLogger(CheckServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserImpl user;
        if (req.getSession().isNew()) {
            resp.sendRedirect("/web/hello.jsp");
        }

        int id  = 0;
        String login =null;
        boolean doesItLogin = false;
        try {
            id = Integer.parseInt( req.getParameter("id"));
            }
        catch (IllegalArgumentException e) {
            login = req.getParameter("id");
            doesItLogin = true;
            }


        String password = req.getParameter("password");

        if (doesItLogin) {
        user = checkUser(login, password);
        }
        else
        user = checkUser(id, password);

        HttpSession session = req.getSession();
        session.setAttribute("user", user);



        if (Role.ADMINISTRATOR.equals(user.getRole())) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("adminActList.jsp");
            requestDispatcher.forward(req,resp);
        }
        else  if (Role.STUDENT.equals(user.getRole())) {
           req.setAttribute("map", new HashMap<Student, String>(Map.of((Student) user, user.getInf())));
           req.getRequestDispatcher("demonstrate.jsp").forward(req, resp);
        }
        else  if (Role.TRAINER.equals(user.getRole())) {
            Group group = DataBaseInf.groupHashMap.values()
                    .stream().filter(g ->g.getTrainer().getId()== user.getId()).findAny().get();
            Map<Integer, Student> studentHashMap =group.getStudentMap();
            Set<Theams> theams = group.getTheamsSet();
            req.setAttribute("set", theams);
            req.setAttribute("map", studentHashMap);

          req.getRequestDispatcher("trainerActList.jsp").forward(req,resp);
         //  RequestDispatcher requestDispatcher = req.getRequestDispatcher("adminControl/trainerList.jsp");

        }

    }

    private UserImpl checkUser (int id, String password) {
     DaoImp daoImp = new DaoImp();
     UserImpl user;
     user = itRightCondition(daoImp.getUsersMapById(id), id, password );
     return  user;
    }

    private UserImpl checkUser (String login, String password) {
         UserImpl user = itRightCondition(login, password);
         return  user;
    }

    private UserImpl itRightCondition (Map <Integer, UserImpl> map, int id, String password)  {
    if (map.get(id).getPassword().equalsIgnoreCase(password))
    return map.get(id);
    return null;
    }

    private UserImpl itRightCondition ( String login, String password)  {
        UserImpl user;
        Map<Integer, UserImpl> allUser = new HashMap<>();
        allUser.putAll(DataBaseInf.adminHashMap);
        allUser.putAll(DataBaseInf.studentHashMap);
        allUser.putAll(DataBaseInf.trainerHashMap);
        for (Map.Entry<Integer, UserImpl> entry: allUser.entrySet()) {
           if (entry.getValue().getLogin().equalsIgnoreCase(login) &&
           entry.getValue().getPassword().equals(password))
           {
               user = entry.getValue();
               return user;
           }
        }
        return null;
    }

}
