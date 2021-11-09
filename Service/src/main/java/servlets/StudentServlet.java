package servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.RepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Mark;
import threadmodel.Theams;
import users.UserImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Servlet
 * providing users with role Student
 * watch himself marks
 **/
@WebServlet("/studentservlet")
public class StudentServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(StudentServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserImpl user = (UserImpl) session.getAttribute("user");
        UserImpl student = RepositoryFactory.getRepository().getUserById(user.getId());
        HashMap<UserImpl, HashMap<Theams, List<Mark>>> studentIndInf = ThreadRepositoryFactory.getRepository().studentTheamMark(user.getId());
        log.info("Student logIn= {}", student.getInf());
        req.setAttribute("student", student);
        req.setAttribute("mapmap", studentIndInf);
        req.getRequestDispatcher("StudentPage/studentinf.jsp").forward(req, resp);
    }
}
