package servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Servlet make change marks of the student by data
 * took from JSP
 **/
@WebServlet("/changeandcreatemark")
public class ChangeAndCreateMarkServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(ChangeAndCreateMarkServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String studentId = req.getParameter("student");
        String theam = req.getParameter("th");
        String[] marks = req.getParameterValues("marks");
        String[] markId = req.getParameterValues("markid");
        String act = req.getParameter("act");

        Group group = (Group) req.getSession().getAttribute("group");

        if (act.equalsIgnoreCase("change")) {
            String answer = doChange(marks, markId, studentId, theam);
            req.setAttribute("set", group.getTheamsSet());
            req.setAttribute("map", group.getStudentMap());
            req.getRequestDispatcher(answer).forward(req, resp);
        }
        String answer = delete(marks, theam, studentId);
        req.setAttribute("set", group.getTheamsSet());
        req.setAttribute("map", group.getStudentMap());
        req.getRequestDispatcher(answer).forward(req, resp);
    }

    private String doChange(String[] marks, String[] markId, String studentId, String theamId) {
        HashMap<Integer, Integer> markIdMarkValue = new HashMap<>();
        int stId;
        int thId;
        try {
            stId = Integer.parseInt(studentId);
            thId = Integer.parseInt(theamId);
        } catch (IllegalArgumentException e) {
            return "exeception.jsp";
        }
        for (int i = 0; i < markId.length; i++) {
            if (marks[i] != null && !marks[i].equals("")) {
                try {
                    int tempMarkId = Integer.parseInt(markId[i]);
                    int tempMarkValue = Integer.parseInt(marks[i]);
                    if (tempMarkValue < 0 || tempMarkValue > 100)  throw new IllegalArgumentException();
                    markIdMarkValue.put(tempMarkId, tempMarkValue);
                    log.info("In dochangeServlet = {}", tempMarkId + " " + tempMarkValue);
                } catch (IllegalArgumentException e) {
                    return "exeception.jsp";
                }
            }

        }
        ThreadRepositoryFactory.getRepository().changeMark(markIdMarkValue, stId, thId);
        return "TrainerControlPage/trainerActList.jsp";
    }

    private String delete(String[] marks, String theam, String studentId) {
        int[] tempMarksId = new int[marks.length];
        try {
            int theamId = Integer.parseInt(theam);
            int studentid = Integer.parseInt(studentId);
            for (int i = 0; i < marks.length; i++) {
                if (!marks[i].equals("")) {
                    int markId = Integer.parseInt(marks[i]);
                    tempMarksId[i] = markId;
                }
            }
            ThreadRepositoryFactory.getRepository().deleteMarksById(tempMarksId, theamId, studentid);

        } catch (IllegalArgumentException e) {
            log.info("Exeception ={}", e.getMessage());
            return "exeception.jsp";
        }

        return "TrainerControlPage/trainerActList.jsp";
    }
}
