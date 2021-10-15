package Servlets;

import DataBase.DataBaseInf;
import Repository.DAO.DaoImp;
import ThreadModel.Salary;
import Users.Trainer;
import Users.UserImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/updateUser")
public class ChangeUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        DaoImp daoImp = new DaoImp();
        UserImpl user = daoImp.getUser(id);
        req.setAttribute("id", user.getId());
        req.setAttribute("role", user.getRole());
        req.setAttribute("login", user.getLogin());
        req.setAttribute("pass", user.getPassword());
        req.setAttribute("name", user.getName());
        req.setAttribute("age", user.getAge());

        req.getRequestDispatcher("adminControl/actionchange.jsp").forward(req, resp);
    }
}



