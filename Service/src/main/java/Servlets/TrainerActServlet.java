package Servlets;

import DAO.DaoImp;
import Repository.RepositoryFactory;
import Repository.ThreadModelRep.ThreadRepositoryImpl;
import ThreadModel.Group;
import ThreadModel.Mark;
import ThreadModel.Theams;
import Users.Student;
import Users.UserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
Servlet
 providing users with role Trainer
 do choose some action with student entity

 **/
@WebServlet("/traineract")
public class TrainerActServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(TrainerActServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("user").equals("") || req.getParameter("th").equals("")) {
            req.getRequestDispatcher("exeception.jsp").forward(req, resp);
        }

        int userID = Integer.parseInt(req.getParameter("user"));
        String theam = req.getParameter("th");
        String act = req.getParameter("act");
        String mark = req.getParameter("mark");
        DaoImp daoImp = new DaoImp();
        Student student = (Student) RepositoryFactory.getRepository().allStudent().get(userID);
        Theams tempth = ThreadRepositoryImpl.getInstance().theamById(Integer.parseInt(theam));


        //  Theams th = Theams.valueOf(theam);

        if (act.equalsIgnoreCase("create")) {
            Group group = (Group) req.getSession().getAttribute("group");
            Map<Integer, UserImpl> studentHashMap = group.getStudentMap();
            Set<Theams> theams = group.getTheamsSet();
            log.info("Act = {}", "Student ={}", "Theam = {}", "Mark = {}", act, student, theam, mark );
            req.setAttribute("set", theams);
            req.setAttribute("map", studentHashMap);
            req.getRequestDispatcher(doAdd(userID, theam, mark)).forward(req, resp);

        } else if (act.equalsIgnoreCase("watch")) {
            req.setAttribute("student", student);
            req.setAttribute("map", getTheamsListHashMap(userID, tempth));
            req.getRequestDispatcher("TrainerControlPage/watchmark.jsp").forward(req, resp);
        } else if (act.equalsIgnoreCase("delete")) {
            req.setAttribute("student", student);
        //    req.setAttribute("map", getHashMapforTheam(th, student));
            req.getRequestDispatcher("TrainerControlPage/deletemark.jsp").forward(req, resp);
        } else if (act.equalsIgnoreCase("change")) {

            req.setAttribute("student", student);
        //    req.setAttribute("map", getHashMapforTheam(th, student));
            req.getRequestDispatcher("TrainerControlPage/listofmarkforchange.jsp").forward(req, resp);
        }
    }

    private HashMap<Theams, List<Mark>> getTheamsListHashMap(int userID, Theams tempth) {
        log.info("In servlet getTheam method = {}", userID+tempth.getTheamName());
        HashMap<Theams, List<Mark>> theamsListHashMap = new HashMap<>(
                Map.of(tempth, ThreadRepositoryImpl.getInstance().getMarkListbyTheam(tempth, userID) ));
        return theamsListHashMap;
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
        } catch (IllegalArgumentException e) {
            return "exeception.jsp";
        }
        log.info("in servlet add ={}", userId+" "+tempTheamID+" " + tempMark);
        ThreadRepositoryImpl.getInstance().addMarkToStudent(userId, tempTheamID, tempMark);

        return result;
    }
}
