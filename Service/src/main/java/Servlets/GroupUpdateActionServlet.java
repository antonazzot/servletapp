package Servlets;

import DataBase.DataBaseInf;
import DAO.DaoImp;
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

        int [] studentInGroupForDeletedId = checkAndConverterParamToInt(req.getParameterValues("grstid"));
        int [] studentForAddId = checkAndConverterParamToInt(req.getParameterValues("astid"));
        int [] theamsInGroupForDeleteId = checkAndConverterParamToInt(req.getParameterValues("astid"));
        int [] theamForAddId = checkAndConverterParamToInt(req.getParameterValues("frth"));
        int newTrainerId = req.getParameter("frtr").equals("") ? 0 : Integer.parseInt(req.getParameter("frtr"));


    }

    private int[] checkAndConverterParamToInt(String[] entity) {
        int [] result = null;
        if (entity!=null) {
           result = new int[entity.length];
            for (int i = 0; i < entity.length; i++) {
                if (entity[i] != null &&!entity[i].equals("")) {
                    try {
                        result [i] = Integer.parseInt(entity[i]);
                    }
                    catch (IllegalArgumentException e) {
                        log.info("EROR = {}", e.getMessage());
                    }
                }
            }
        }
        return result;
    }
}


