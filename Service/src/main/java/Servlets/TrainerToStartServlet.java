package Servlets;

import DataBase.DataBaseInf;
import ThreadModel.Group;
import Users.Trainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("TrainerToStartServlet")
public class TrainerToStartServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(TrainerToStartServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Trainer trainer = (Trainer) req.getSession().getAttribute("user");
        Group group = DataBaseInf.groupHashMap.values().stream().
                filter(g->g.getTrainer().
                        equals(trainer)).findFirst().
                            orElse(null);

        if (group!=null) {
            logger.info("trainer = {}", "group = {}", trainer.getInf(), group.getInf());
        req.setAttribute("map", group.getStudentMap());
        req.getRequestDispatcher("adminControl/trainerList.jsp").forward(req, resp);}
        else resp.getWriter().write("Группа еще не создана, обратитесь к администратору");
    }

}
