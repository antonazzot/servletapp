package servlets;

import repository.threadmodelrep.ThreadRepositoryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 Servlet add salary to the trainer by inf
 took in JSP
 **/
@WebServlet("/addSalaryForTrainer")
public class SalaryAdderServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(SalaryAdderServlet.class);
    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String trainerId = req.getParameter("trainer");
        String sal = req.getParameter("sal");
        req.getRequestDispatcher(salaryresultadder(trainerId, sal)).forward(req, resp);
    }

    private String salaryresultadder(String tr, String sal) {
        String result = null;
         if (tr != null && sal != null) {
            try {
                int id = Integer.parseInt(tr);
                int salary = Integer.parseInt(sal);
                ThreadRepositoryFactory.getRepository().addSalaryToTrainer(id, salary);
                result = "adminControl/adminActList.jsp";
            } catch (IllegalArgumentException e) {
                result = "exeception.jsp";
            }

        }
        return result;

    }

}


