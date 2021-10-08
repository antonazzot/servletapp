package Servlets;

import Servlets.DAO.DaoImp;
import ThreadModel.Group;
import ThreadModel.Theams;
import Users.Student;
import Users.Trainer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet ("/GroupCreaterServlet")
public class GroupCreaterServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String [] theams = req.getParameterValues("th");
        String trainer = req.getParameter("trainer");
        String [] student = req.getParameterValues("user");
       int trainerID = 0;
        try {trainerID = Integer.parseInt(trainer);}
        catch (IllegalArgumentException e ) {

        }

        DaoImp daoImp =  new DaoImp();
        HashMap<Integer, Student> studentMap = studentMapCreater(student);
        Set<Theams> theamsSet = theamsSetcreater(theams);
        Trainer trainer1 = (Trainer) daoImp.getUser(trainerID);
        Group group = new Group(trainer1, studentMap, theamsSet);
        req.getRequestDispatcher("adminActList.jsp").forward(req, resp);

    }

    private Set<Theams> theamsSetcreater(String[] theams) {
        Set <Theams> result = new HashSet<>();
        for (int i = 0; i < theams.length; i++) {
            for (int j=0; j<Theams.values().length; j++) {
                if (theams[i].equalsIgnoreCase(Theams.values()[j].name())){
                    result.add(Theams.values()[j]);
                }
            }
        }
        return result;
    }

    private HashMap<Integer, Student> studentMapCreater(String [] student) {
        int temp [] = new int [student.length];
        DaoImp daoImp =  new DaoImp();
        HashMap<Integer, Student> result = new HashMap<>();
        for (int i = 0; i < student.length; i++) {
            temp[i]=Integer.parseInt(student[i]);
            Student student1 = (Student) daoImp.getUser(temp[i]);
            result.put(student1.getId(), student1);
        }
        return result;
    }
}
