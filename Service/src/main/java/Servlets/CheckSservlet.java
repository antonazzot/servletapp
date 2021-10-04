package Servlets;
import DataBase.DataBaseInf;
import Servlets.DAO.DaoImp;
import Users.Role;
import Users.UserImpl;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet (value = "/checkUser")
public class CheckSservlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String rolestring = req.getParameter("role");
    int id = Integer.parseInt( req.getParameter("id")) ;
    String password = req.getParameter("password");

    UserImpl user = checkUser(id, password);

    if (user.getRole().equals(Role.ADMINISTRATOR)) {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("administratorPage.jsp");
        requestDispatcher.forward(req,resp);
    }

    }

    private UserImpl checkUser (int id, String password) {
     DaoImp daoImp = new DaoImp();
     UserImpl user = null;

     user = itRightCondition(daoImp.getUsersMapById(id), id, password );
     return  user;
    }

    private UserImpl itRightCondition (Map <Integer, UserImpl> map, int id, String password)  {
            if (map.get(id).getPassword().equalsIgnoreCase(password))
                return map.get(id);
            return null;
        }


}
