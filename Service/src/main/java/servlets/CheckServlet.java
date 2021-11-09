package servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Group;
import threadmodel.Theams;
import users.Role;
import users.UserImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Servlet providing  authorizes the user and redirects to a specific page
 * //In first startfilter do authentication by id and login, and authorization,
 * but I decided to divide it into different blocks
 **/

@WebServlet(value = "/checkUser")
public class CheckServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CheckServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserImpl user = (UserImpl) req.getSession().getAttribute("user");
        HttpSession session = req.getSession();
        //authorization block
        if (Role.ADMINISTRATOR.equals(user.getRole())) {
            logger.info("UserRole ={}", user.getRole());
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("adminControl/adminActList.jsp");
            requestDispatcher.forward(req, resp);
        } else if (Role.STUDENT.equals(user.getRole())) {
            logger.info("UserRole ={}", user.getRole());
            req.getRequestDispatcher("StudentPage/studentstartpage.jsp").forward(req, resp);
        } else if (Role.TRAINER.equals(user.getRole())) {
            logger.info("UserRole ={}", user.getRole());
            if (ThreadRepositoryFactory.getRepository().allGroup()
                    .values()
                    .stream()
                    .anyMatch(g -> g.getTrainer().getId() == user.getId())) {
                Group group = ThreadRepositoryFactory.getRepository().allGroup()
                        .values()
                        .stream()
                        .filter(g -> g.getTrainer().getId() == user.getId())
                        .findAny()
                        .get();

                Map<Integer, UserImpl> studentHashMap = group.getStudentMap();
                Set<Theams> theams = group.getTheamsSet();
                session.setAttribute("group", group);
                req.setAttribute("set", theams);
                req.setAttribute("map", studentHashMap);
                req.getRequestDispatcher("TrainerControlPage/trainerActList.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("TrainerControlPage/groupnotexist.jsp").forward(req, resp);
            }
        }

    }
}
