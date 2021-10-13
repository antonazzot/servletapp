package Servlets;

import Servlets.DAO.DaoImp;
import Servlets.Filters.StartFilter;
import ThreadModel.Group;
import ThreadModel.Mark;
import ThreadModel.Theams;
import Users.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/changeandcreatemark")
public class ChangeAndCreateMarkServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(ChangeAndCreateMarkServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String studentId = req.getParameter("student");
        String theam = req.getParameter("th");
        String [] marks = req.getParameterValues("marks");
        String mark = req.getParameter("mark");
        String act = req.getParameter("act");
        DaoImp daoImp = new DaoImp();
        Student student = (Student) daoImp.getUser(Integer.parseInt(studentId));
        Group group = (Group) req.getSession().getAttribute("group");
        if (act.equalsIgnoreCase("change")) {
            req.setAttribute("set", group.getTheamsSet());
            req.setAttribute("map", group.getStudentMap());
            log.info("Trainer = {}", "Student = {}", "Mark = {}", req.getSession().getAttribute("user"), student, mark);
            req.getRequestDispatcher(change(theam, marks, student)).forward(req, resp);
        }
        req.setAttribute("set", group.getTheamsSet());
        req.setAttribute("map", group.getStudentMap());
        req.getRequestDispatcher(delete(theam, mark, student)).forward(req, resp);
    }

    private String change(String theam, String[] mark, Student student) {
        for (int i = 0; i < mark.length; i++) {
            if (mark[i] != null) {
                try {
                    int markvalues = Integer.parseInt(mark[i]);
                    student.getListOfMark()
                            .get(Theams.valueOf(theam))
                            .get(i)
                            .setValuesOfMark(markvalues);
                } catch (IllegalArgumentException e) {
                    return "exeception.jsp";
                }
            }
            else return "TrainerControlPage/trainerActList.jsp";
        }
        return "TrainerControlPage/trainerActList.jsp";
    }

    private String delete(String theam, String mark, Student student) {
        try {
            int tempMark = Integer.parseInt(mark);
            Mark temp = student.getListOfMark().get(Theams.valueOf(theam)).stream()
                    .filter(m -> m.getValuesOfMark() == tempMark)
                    .findAny().get();
            student.getListOfMark().get(Theams.valueOf(theam)).remove(temp);
        } catch (IllegalArgumentException e) {
            return "exeception.jsp";
        }

        return "TrainerControlPage/trainerActList.jsp";
    }
}
