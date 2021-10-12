package Servlets;

import DataBase.DataBaseInf;
import ThreadModel.Mark;
import ThreadModel.Salary;
import ThreadModel.Theams;
import Users.Administrator;
import Users.Role;
import Users.Student;
import Users.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet ("/hello")
public class StartPage extends HttpServlet {
    Logger log = LoggerFactory.getLogger(StartPage.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        {
            ServletContext servletContext = getServletContext();
            String adminLogin = servletContext.getInitParameter("AdminLogin");
            String adminPassword = servletContext.getInitParameter("AdminPassword");
            if (!DataBaseInf.adminHashMap.containsValue(DataBaseInf.adminHashMap.get(1))) {
                Administrator administrator = new Administrator( "Anton", adminLogin, adminPassword, 30);

               Student student = new Student("Anton Tsyrkunou" , "ant", "pas", 27
               , new HashMap<>(Map.of(Theams.MUSIC, new ArrayList<>(List.of(new Mark(50),new Mark(50)
               ,new Mark(70),new Mark(90), new Mark(100))))));
               DataBaseInf.studentHashMap.put(student.getId(), student);

                Trainer trainer = new Trainer("Alex", "Ale", "pppas", 40,
                        new ArrayList<>(List.of(new Salary(new BigDecimal(4000)), new Salary(new BigDecimal(3000)) )));
        }
            log.info("Admin = {}", "Pass ={}", adminLogin, adminPassword);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("hello.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}

