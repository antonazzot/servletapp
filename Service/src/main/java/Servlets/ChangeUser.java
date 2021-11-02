package Servlets;

import Repository.RepositoryFactory;
import Users.UserImpl;

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
@WebServlet("/updateUser")
public class ChangeUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        UserImpl user = RepositoryFactory.getRepository().getUserById(id);
        req.setAttribute("id", user.getId());
        req.setAttribute("role", user.getRole());
        req.setAttribute("login", user.getLogin());
        req.setAttribute("pass", user.getPassword());
        req.setAttribute("name", user.getName());
        req.setAttribute("age", user.getAge());

        RepositoryFactory.getRepository().updateUser(user);
        req.getRequestDispatcher("adminControl/actionchange.jsp").forward(req, resp);
    }
}



