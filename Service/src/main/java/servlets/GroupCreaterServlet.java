package servlets;

import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import users.UserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 Servlet create  the group by data
 took from JSP
 **/
@WebServlet("/GroupCreaterServlet")
public class GroupCreaterServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(GroupCreaterServlet.class);
    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

}
