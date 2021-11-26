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
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * Servlet make calculate avarage salary
 **/
@WebServlet("/calculateAvarageSalary")
public class CalculateAvarageSalaryServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String trainerId = req.getParameter("trainer");
        String sal = req.getParameter("sal");
        Trainer trainer = null;
        long longavaragessalary = 0;
        Writer writer = resp.getWriter();
        if (trainerId != null) {
            int id = Integer.parseInt(trainerId);
            Map<Trainer, List<Salary>> trainerListHashMap = ThreadRepositoryFactory.getRepository().trainerSalary();
            trainer = trainerListHashMap.keySet().stream().filter(t -> t.getId() == id).findAny().get();
            try {
                int avarageValue = Integer.parseInt(sal);
                if (avarageValue > trainer.getSalarylist().size()) {
                    writer.write("ВВедено неверное значение");
                } else
                    longavaragessalary = avarageSalaryCalc(trainer.getSalarylist(), avarageValue);
            } catch (IllegalArgumentException e) {
                writer.write("ВВедено неверное значение");
            }
        } else writer.write("ВВедено неверное значение");
        req.setAttribute("trainer", trainer);
        req.setAttribute("avarage", longavaragessalary);
        req.getRequestDispatcher("adminControl/avaragesalarywatch.jsp").forward(req, resp);
    }

    private long avarageSalaryCalc(List<Salary> salaryArrayList, int time) {
        long avarageSalary = 0;

        for (int i = time; i > 0; i--) {
            int temp = salaryArrayList.size() - i;
            avarageSalary = salaryArrayList.get(temp).getBigDecimalSalary().longValue() + avarageSalary;
        }
        avarageSalary = avarageSalary / time;
        return avarageSalary;
    }
}

