package servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;
import threadmodel.Mark;
import threadmodel.Theams;
import users.Student;
import users.UserImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Servlet
 * providing users with role Trainer
 * do choose some action with student entity
 **/
@WebServlet("/traineract")
public class TrainerActServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(TrainerActServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("user") == null ||
                req.getParameter("user").equals("")
        ) {
            req.getRequestDispatcher("exeception.jsp").forward(req, resp);
        }

        int userID = Integer.parseInt(req.getParameter("user"));
        String theam = req.getParameter("th");
        String act = req.getParameter("act");
        String mark = req.getParameter("mark");
        Student student = (Student) RepositoryFactory.getRepository().allStudent().get(userID);
        Theams tempth = ThreadRepositoryFactory.getRepository().theamById(Integer.parseInt(theam));

        if (act.equalsIgnoreCase("create")) {
            Group group = (Group) req.getSession().getAttribute("group");
            Map<Integer, UserImpl> studentHashMap = group.getStudentMap();
            Set<Theams> theams = group.getTheamsSet();
            log.info("Act = {}", "Student ={}", "Theam = {}", "Mark = {}", act, student, theam, mark);
            String answer = doAdd(userID, theam, mark);
            req.setAttribute("set", theams);
            req.setAttribute("map", studentHashMap);
            req.getRequestDispatcher(answer).forward(req, resp);
        } else if (act.equalsIgnoreCase("watch")) {
            req.setAttribute("student", student);
            req.setAttribute("map", getTheamsListHashMap(userID, tempth));
            req.getRequestDispatcher("TrainerControlPage/watchmark.jsp").forward(req, resp);
        } else if (act.equalsIgnoreCase("delete")) {
            req.setAttribute("student", student);
            req.setAttribute("th", tempth);
            req.setAttribute("map", ThreadRepositoryFactory.getRepository().getMarkIDListbyTheam(tempth, userID));
            req.getRequestDispatcher("TrainerControlPage/deletemark.jsp").forward(req, resp);
        } else if (act.equalsIgnoreCase("change")) {
            req.setAttribute("student", student);
            req.setAttribute("th", tempth);
            req.setAttribute("map", ThreadRepositoryFactory.getRepository().getMarkIDListbyTheam(tempth, userID));
            req.getRequestDispatcher("TrainerControlPage/listofmarkforchange.jsp").forward(req, resp);
        }
    }

    private HashMap<Theams, List<Mark>> getTheamsListHashMap(int userID, Theams tempth) {
        UserImpl student = RepositoryFactory.getRepository().getUserById(userID);
        HashMap<Theams, List<Mark>> result = new HashMap<>();
        result.put(tempth, ThreadRepositoryFactory.getRepository().getMarkListbyTheam(tempth, student.getId()));
        return result;
    }

    private HashMap<Theams, List<Mark>> getHashMapforTheam(Theams th, Student student) {
        HashMap<Theams, List<Mark>> result = new HashMap<>();
        result.put(th, student.getListOfMark().get(th));
        return result;
    }

    private String doAdd(int userId, String theam, String mark) {
        int tempMark;
        int tempTheamID;
        String result = "TrainerControlPage/trainerActList.jsp";
        try {
            tempMark = Integer.parseInt(mark);
            tempTheamID = Integer.parseInt(theam);
            if (tempMark < 0 || tempMark > 100)  throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            return "exeception.jsp";
        }
        log.info("in servlet add ={}", userId + " " + tempTheamID + " " + tempMark);
        ThreadRepositoryFactory.getRepository().addMarkToStudent(userId, tempTheamID, tempMark);
        return result;
    }
}
