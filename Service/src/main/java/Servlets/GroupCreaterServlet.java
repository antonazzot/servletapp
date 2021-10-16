package Servlets;

import Repository.DAO.DaoImp;
import ThreadModel.Group;
import ThreadModel.Theams;
import Users.Student;
import Users.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
/**
 Servlet create  the group by data
 took from JSP
 **/
@WebServlet("/GroupCreaterServlet")
public class GroupCreaterServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(GroupCreaterServlet.class);
    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] theams = req.getParameterValues("th");
        int trainerID = Integer.parseInt(req.getParameter("trainer"));
        String[] student = req.getParameterValues("user");
        try {
        DaoImp daoImp = new DaoImp();
        Set<Theams> theamsSet = theamsSetcreater(theams);
        HashMap<Integer, Student> studentMap = studentMapCreater(student, theamsSet);
        Trainer trainer = (Trainer) daoImp.getUser(trainerID);
        Group group = new Group(trainer, studentMap, theamsSet);
        log.info("Group = {}",  group);
        req.getRequestDispatcher("adminControl/adminActList.jsp").forward(req, resp);}
        catch (IllegalArgumentException e) {
            log.info("Values not change = {}", theams, trainerID, student);
            req.getRequestDispatcher("exeception.jsp").forward(req, resp);
        }
    }

    private Set<Theams> theamsSetcreater(String[] theams) {
        Set<Theams> result = new HashSet<>();
        for (String theam : theams) {
            for (int j = 0; j < Theams.values().length; j++) {
                if (theam.equalsIgnoreCase(Theams.values()[j].name())) {
                    result.add(Theams.values()[j]);
                }
            }
        }
        return result;
    }

    private HashMap<Integer, Student> studentMapCreater(String[] student, Set<Theams> theamsSet) {
        int[] temp = new int[student.length];
        DaoImp daoImp = new DaoImp();
        HashMap<Integer, Student> result = new HashMap<>();
        for (int i = 0; i < student.length; i++) {
            temp[i] = Integer.parseInt(student[i]);
            Student student1 = (Student) daoImp.getUser(temp[i]);
            for (Theams th :
                    theamsSet) {
                student1.addTheam(th);
            }
            result.put(student1.getId(), student1);
        }
        return result;
    }


}
