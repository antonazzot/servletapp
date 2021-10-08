package Servlets;

import DataBase.DataBaseInf;
import Servlets.DAO.DaoImp;
import ThreadModel.Group;
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
@WebServlet("/TrainerToStartServlet")
public class TrainerToStartServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(TrainerToStartServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


       UserImpl user = (UserImpl) req.getAttribute("user");
       DaoImp daoImp = new DaoImp();
       Trainer trainer = (Trainer) daoImp.getUser(user.getId());
        Group group = DataBaseInf.groupHashMap.values().stream().
                filter(g->g.getTrainer().getId()==
                        (trainer.getId())).findFirst().get();


        logger.info("trainer = {}", "group = {}", trainer.getInf(), group.getInf());

        req.setAttribute("map", group.getStudentMap());
        req.getRequestDispatcher("adminControl/trainerList.jsp").forward(req, resp);


        // resp.getWriter().write("Группа еще не создана, обратитесь к администратору");
    }

}
