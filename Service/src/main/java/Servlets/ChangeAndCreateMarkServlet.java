package Servlets;

import DAO.DaoImp;
import Repository.ThreadModelRep.ThreadRepositoryImpl;
import ThreadModel.Group;
import Users.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 Servlet make change marks of the student by data
 took from JSP
 **/
@WebServlet("/changeandcreatemark")
public class ChangeAndCreateMarkServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(ChangeAndCreateMarkServlet.class);
    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String studentId = req.getParameter("student");
        String theam = req.getParameter("th");
        String [] marks = req.getParameterValues("marks");
        String [] markId = req.getParameterValues("markid");
        String act = req.getParameter("act");
        DaoImp daoImp = new DaoImp();
        Student student = (Student) daoImp.getUser(Integer.parseInt(studentId));
        Group group = (Group) req.getSession().getAttribute("group");

        if (act.equalsIgnoreCase("change")) {
            String answer = doChange(marks, markId);
            req.setAttribute("set", group.getTheamsSet());
            req.setAttribute("map", group.getStudentMap());
            req.getRequestDispatcher(change(theam, marks, student)).forward(req, resp);
        }
        String answer = delete(marks);
        req.setAttribute("set", group.getTheamsSet());
        req.setAttribute("map", group.getStudentMap());
        req.getRequestDispatcher(answer).forward(req, resp);
    }

    private String doChange(String[] marks, String[] markId) {
        HashMap <Integer, Integer> markIdMarkValue = new HashMap<>();
        for (int i = 0; i < markId.length; i++) {
            if (marks[i]!=null && !marks[i].equals("")) {
            try {
              int  tempMarkId = Integer.parseInt(markId[i]);
              int  tempMarkValue = Integer.parseInt(marks[i]);
              markIdMarkValue.put(tempMarkId, tempMarkValue);
                log.info("In dochangeServlet = {}", tempMarkId + " " + tempMarkValue );
            }
            catch (IllegalArgumentException e) {
                return "exeception.jsp";
            }
            }

        }
        ThreadRepositoryImpl.getInstance().changeMark(markIdMarkValue);
        return "TrainerControlPage/trainerActList.jsp";
    }

    private String change(String theam, String[] mark, Student student) {
//        for (int i = 0; i < mark.length; i++) {
//
//                if (mark[i] != null && !mark[i].equals("")) {
//                        try {
//                            int markvalues = Integer.parseInt(mark[i]);
//                    student.getListOfMark()
//                            .get(Theams.valueOf(theam))
//                            .get(i)
//                            .setValuesOfMark(markvalues);}
//
//                 catch (IllegalArgumentException e) {
//                    return "exeception.jsp";
//                }
//                }
//        }

        return "TrainerControlPage/trainerActList.jsp";
    }

    private String delete(String [] marks) {
        int[] tempMarksId = new int[marks.length];
        try {
            for (int i = 0; i < marks.length; i++) {
                int markId = Integer.parseInt(marks[i]);
                tempMarksId [i] = markId;
            }
            ThreadRepositoryImpl.getInstance().deleteMarksById(tempMarksId);

        }
        catch (IllegalArgumentException e) {
            log.info("Exeception ={}", e.getMessage() );
            return "exeception.jsp";
                    }

        return "TrainerControlPage/trainerActList.jsp";
    }
}
