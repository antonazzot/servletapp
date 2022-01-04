package servlets;

import repository.threadmodelrep.ThreadRepositoryFactory;
import repository.threadmodelrep.ThreadRepositoryImplPostgres;
import threadmodel.Salary;
import users.Trainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Servlet give information to jsp for  calculate avarage salary
 **/
@WebServlet("/avarageSalary")
public class AvarageSalaryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("map", salaryHashMap());
        req.getRequestDispatcher("adminControl/averagesalary.jsp").forward(req, resp);
    }

    private Map<Trainer, List<Salary>> salaryHashMap() {
        return ThreadRepositoryFactory.getRepository().trainerSalary();
    }

}



