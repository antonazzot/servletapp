package servlets;

import repository.threadmodelrep.ThreadRepositoryFactory;
import threadmodel.Salary;
import users.Trainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Servlet  provides  information to JSP
 * for add salary to the trainer
 **/
@WebServlet("/addSalary")
public class TrainerAddSalaryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("map", salaryHashMap());
        req.getRequestDispatcher("adminControl/addsalarypage.jsp").forward(req, resp);
    }

    private HashMap<Trainer, List<Salary>> salaryHashMap() {
        return ThreadRepositoryFactory.getRepository().trainerSalary();
    }
}



