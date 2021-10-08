package Servlets;

import Servlets.DAO.DaoImp;
import ThreadModel.Theams;
import Users.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//http://localhost:8080/web/traineract?user=2&th=LANGVAGE&act=change&mark=
@WebServlet ("/changeandcreatemark")
public class ChangeAndCreateMarkServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user =  req.getParameter("user");
        String theam = req.getParameter("th");
        String act =  req.getParameter("act");
        String mark = req.getParameter("mark");
        DaoImp daoImp = new DaoImp();
        Student student = (Student) daoImp.getUser(Integer.parseInt(user));
        if (act.equalsIgnoreCase("delete")) {
            student.getListOfMark().get(Theams.valueOf(theam)).remove(0);

        }
    }
}
