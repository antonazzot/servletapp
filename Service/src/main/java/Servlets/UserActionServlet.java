package Servlets;

import Servlets.DAO.DaoImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet ("/userActionsServlet")
public class UserActionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String act = req.getParameter("act");
        String user = req.getParameter("user");
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String age = req.getParameter("age");
        DaoImp daoImp = new DaoImp();
        if (user.equalsIgnoreCase("student")){
            if(act.equalsIgnoreCase("add")){

            }
        }
    }
}
