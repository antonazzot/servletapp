package Servlets;

import Servlets.DAO.DaoImp;
import ThreadModel.Group;
import ThreadModel.Mark;
import ThreadModel.Theams;
import Users.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet("/traineract")
public class TrainerActServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String user =  req.getParameter("user");
      String theam = req.getParameter("th");
      String act =  req.getParameter("act");
      String mark = req.getParameter("mark");
      DaoImp daoImp = new DaoImp();
      Student student = (Student) daoImp.getUser(Integer.parseInt(user));


        if (act.equalsIgnoreCase("create")) {
            Group group = (Group) req.getSession().getAttribute("group");
            Map<Integer, Student> studentHashMap =group.getStudentMap();
            Set<Theams> theams = group.getTheamsSet();
            req.setAttribute("set", theams);
            req.setAttribute("map", studentHashMap);
            req.getRequestDispatcher(doAdd(student, theam, mark)).forward(req, resp);

        }
         else  {

             req.setAttribute("map", student.getListOfMark());
             req.setAttribute("student", student);
        //    req.setAttribute("map", getIntegerIntegerHashMap(theam, student));
         //   HashMap<List<Mark>, Student> studentHashMap = new HashMap<>();
        //    studentHashMap.put( student.getListOfMark().get(Theams.valueOf(theam)), student);
        //    req.setAttribute("thst" , studentHashMap);
            req.getRequestDispatcher("listofmarkforchange.jsp").forward(req, resp);
        }

    }

    private HashMap<Integer, Integer> getIntegerIntegerHashMap(String theam, Student student) {
        ArrayList <Mark> markArrayList = (ArrayList<Mark>) student.getListOfMark().get(Theams.valueOf(theam));
        HashMap <Integer, Integer> marksWithIndex  = new HashMap<>();
        for (int i = 0; i < markArrayList.size(); i++) {
            marksWithIndex.put(i,markArrayList.get(i).getValuesOfMark());
        }
        return marksWithIndex;
    }

    private String doAdd(Student student,  String theam ,String mark) {
        int tempMark = 0;
        String result = "trainerActList.jsp";
        try {
            tempMark = Integer.parseInt(mark);
        }
        catch (IllegalArgumentException e) {
            return "exeception.jsp";
        }

            student.getListOfMark().get(Theams.valueOf(theam)).add(new Mark(Integer.parseInt(mark)));
            return result;
    }
}
