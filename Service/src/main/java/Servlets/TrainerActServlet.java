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
      Theams th = Theams.valueOf(theam);


        if (act.equalsIgnoreCase("create")) {
            Group group = (Group) req.getSession().getAttribute("group");
            Map<Integer, Student> studentHashMap =group.getStudentMap();
            Set<Theams> theams = group.getTheamsSet();
            req.setAttribute("set", theams);
            req.setAttribute("map", studentHashMap);
            req.getRequestDispatcher(doAdd(student, theam, mark)).forward(req, resp);

        }
         else
         if (act.equalsIgnoreCase("watch"))
        {
            req.setAttribute("student", student);
            req.setAttribute("map", student.getListOfMark());
            req.getRequestDispatcher("watchmark.jsp").forward(req,resp);
        }
         else  if  (act.equalsIgnoreCase("delete")) {
             req.setAttribute("student", student);
             req.setAttribute("map", getHashMapforTheam(th, student));
             req.getRequestDispatcher("deletemark.jsp").forward(req,resp);
             }
         else  if  (act.equalsIgnoreCase("change")) {
             req.setAttribute("student", student);
             req.setAttribute("map", getHashMapforTheam(th, student));
             req.getRequestDispatcher("listofmarkforchange.jsp").forward(req,resp);
         }
         }




    private HashMap<Theams, List<Mark>> getHashMapforTheam (Theams th, Student student) {
        HashMap<Theams, List<Mark>> result =new HashMap<>();
        result.put(th,  student.getListOfMark().get(th));

        return result;
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
            if (student.getListOfMark().containsKey(theam)) {
            student.getListOfMark().get(Theams.valueOf(theam)).add(new Mark(Integer.parseInt(mark)));}
            else {
                student.addTheam(Theams.valueOf(theam));
                student.getListOfMark().get(Theams.valueOf(theam)).add(new Mark(Integer.parseInt(mark)));
            }
            return result;
    }
}
