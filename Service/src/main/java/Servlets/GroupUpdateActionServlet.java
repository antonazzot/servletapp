package Servlets;

import DataBase.DataBaseInf;
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
/**
 Servlet make change in the group by data
 took from JSP
 this servlet is not worked out properly and does not behave as expected, requires refactoring
 **/
@WebServlet("/updateActionGroup")
public class GroupUpdateActionServlet extends HttpServlet {
    static final Logger log = LoggerFactory.getLogger(UserActionServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Group group = DataBaseInf.groupHashMap.get(Integer.parseInt(req.getParameter("id")));
        String[] studentInGroupForDeleted = req.getParameterValues("grstid");
        String[] studentForAdd = req.getParameterValues("astid");
        String[] theamsInGroupForDelete = req.getParameterValues("astid");
        String[] theamForAdd = req.getParameterValues("frth");
        int trainerInGroupForDelete=0;
        int trainerForAdd = 0;
        try { if (req.getParameter("grtr")!=null || req.getParameter("frtr")!=null) {

        trainerInGroupForDelete = Integer.parseInt(req.getParameter("grtr"));
        trainerForAdd = Integer.parseInt(req.getParameter("frtr"));}

        DaoImp daoImp = new DaoImp();
            if (studentInGroupForDeleted.length!=0) {
                for (String s : studentInGroupForDeleted) {
                    if (!s.equals(""))
                        group.getStudentMap().remove(Integer.parseInt(s));
                }
            }
            if (studentForAdd.length!=0) {
                for (String s : studentForAdd) {
                    if (!s.equals("")) {
                        Student student = (Student) daoImp.getUser(Integer.parseInt(s));
                        group.getStudentMap().put(student.getId(), student);
                    }
                }
            }
            if (theamsInGroupForDelete.length!=0) {
                for (String s : theamsInGroupForDelete) {
                    if (!s.equals(""))
                        group.getTheamsSet().remove(Theams.valueOf(s.toUpperCase()));
                }
            }
            if (theamForAdd.length!=0) {
                for (String s : theamForAdd) {
                    if (!s.equals(""))
                        group.getTheamsSet().add(Theams.valueOf(s.toUpperCase()));
                }
            }
        if (trainerInGroupForDelete == 0 ) {
            group.setTrainer(null);
        }
        Trainer trainer = (Trainer) daoImp.getUser(trainerForAdd);
        group.setTrainer(trainer);}
        catch (IllegalArgumentException e) {
            req.getRequestDispatcher("exeception.jsp").forward(req, resp);
        }

        log.info("Group wass update = {}", group, group.getId(), group.getTrainer(), group.getStudentMap());
        req.getRequestDispatcher("adminControl/adminActList.jsp").forward(req, resp);
    }
}


