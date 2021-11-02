package Servlets;

import DAO.DaoImp;
import Repository.RepositoryFactory;
import Repository.ThreadModelRep.ThreadRepositoryFactory;
import Repository.ThreadModelRep.ThreadRepositoryImpl;
import ThreadModel.Group;
import ThreadModel.Theams;
import Users.Student;
import Users.Trainer;
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
 Servlet create  the group by data
 took from JSP
 **/
@WebServlet("/GroupCreaterServlet")
public class GroupCreaterServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(GroupCreaterServlet.class);
    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

       String [] thIdStr = req.getParameterValues("th");
       ArrayList <Integer> thId = (ArrayList<Integer>) Arrays.stream(thIdStr)
               .map(Integer::parseInt)
               .collect(Collectors.toList());

        int trainerID = Integer.parseInt(req.getParameter("trainer"));

        List <UserImpl> studentList = Arrays.stream(req.getParameterValues("user"))
                .map(u -> RepositoryFactory.getRepository()
                        .getUserById(Integer.parseInt(u))).collect(Collectors.toList());
        try {
            ThreadRepositoryFactory.getRepository().addGroup(studentList, thId, trainerID);
        req.getRequestDispatcher("adminControl/adminActList.jsp").forward(req, resp);}
        catch (IllegalArgumentException e) {

            req.getRequestDispatcher("exeception.jsp").forward(req, resp);
        }
    }

//    private Set<Theams> theamsSetcreater(String[] theams) {
//        Set<Theams> result = new HashSet<>();
//        for (String theam : theams) {
//            for (int j = 0; j < Theams.values().length; j++) {
//                if (theam.equalsIgnoreCase(Theams.values()[j].name())) {
//                    result.add(Theams.values()[j]);
//                }
//            }
//        }
//        return result;
//    }

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
