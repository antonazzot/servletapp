package Servlets;

import DataBase.DataBaseInf;
import ThreadModel.Mark;
import ThreadModel.Theams;
import Users.Administrator;
import Users.Role;
import Users.Student;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet ("/hello")
public class StartPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        {
            ServletContext servletContext = getServletContext();
            String adminLogin = servletContext.getInitParameter("AdminLogin");
            String adminPassword = servletContext.getInitParameter("AdminPassword");
            if (!DataBaseInf.adminHashMap.containsValue(DataBaseInf.adminHashMap.get(1))) {
                Administrator administrator = new Administrator( "Anton", adminLogin, adminPassword, 30);
               Student student = new Student("Anton Tsyrkunou" , "ant", "pas", 27
               , new HashMap<>(Map.of(Theams.MUSICK, new ArrayList<>(List.of(new Mark(50),new Mark(50)
               ,new Mark(70),new Mark(90), new Mark(100))))));
               DataBaseInf.studentHashMap.put(student.getId(), student);
            }

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("hello.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}

