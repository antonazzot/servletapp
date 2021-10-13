package Servlets;

import Repository.DAO.DaoImp;
import ThreadModel.Salary;
import Users.Trainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.List;


@WebServlet("/calculateAvarageSalary")
public class CalculateAvarageSalaryServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String trainerId = req.getParameter("trainer");
        String sal = req.getParameter("sal");
        DaoImp daoImp = new DaoImp();
        Trainer trainer = null;
        long longavaragessalary = 0;
        BigDecimal avarageSalary = null;
        Writer writer = resp.getWriter();
        if (trainerId != null) {
            int id = Integer.parseInt(trainerId);
            trainer = (Trainer) daoImp.getUser(id);

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

