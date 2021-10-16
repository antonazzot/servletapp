package Servlets;

import DataBase.DataBaseInf;
import Users.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 It's start servlet
 witch create user with Admin role by context inf,
 and make some session revision
 **/
@WebServlet("/hello")
public class StartPage extends HttpServlet {
    Logger log = LoggerFactory.getLogger(StartPage.class);
    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        {
            ServletContext servletContext = getServletContext();
            String adminLogin = servletContext.getInitParameter("AdminLogin");
            String adminPassword = servletContext.getInitParameter("AdminPassword");
            HttpSession session = req.getSession();
            if (!DataBaseInf.adminHashMap.containsValue(DataBaseInf.adminHashMap.get(1))) {
                Administrator administrator = new Administrator("Anton", adminLogin, adminPassword, 30);
            }
            log.info("Admin = {}", "Pass ={}", adminLogin, adminPassword);

            if (session.getAttribute("user")!=null)  {
            UserImpl user = (UserImpl) session.getAttribute("user");
            req.getRequestDispatcher("/checkUser").forward(req, resp);
                }
            else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("hello.jsp");
            requestDispatcher.forward(req, resp);}
    }

}
}

